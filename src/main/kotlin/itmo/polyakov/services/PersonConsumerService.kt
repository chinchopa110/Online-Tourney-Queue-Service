package itmo.polyakov.services

import itmo.polyakov.DTO.Card
import itmo.polyakov.DTO.Person
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class PersonConsumerService(
    private val cardService: CardService,
    private val forwardService: ForwardService
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logger = LoggerFactory.getLogger(PersonConsumerService::class.java)
    private val combinedDataChannel = Channel<Map<String, Any>>(
        capacity = 10000,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    private val limitedParallelism = Dispatchers.IO.limitedParallelism(100)

    @KafkaListener(topics = ["queue"], groupId = "person-consumer-group")
    suspend fun consume(person: Person) = withContext(limitedParallelism) {
        coroutineScope.launch {
            try {
                processPersonWithCard(person)
            } catch (e: Exception) {
                logger.info("Error processing person ${person.id}: ${e.message}")
            }
        }
    }

    private suspend fun processPersonWithCard(person: Person)  {
        val cardDeferred = coroutineScope.async {
            cardService.getCardByPersonId(person.id)
        }
        val card = cardDeferred.await()

        if (card != null) {
            val combinedData = mapOf(
                "person" to person,
                "card" to card
            )

            combinedDataChannel.send(combinedData)

            coroutineScope.launch {
                forwardService.sendCombinedData(person, card)
            }
        } else {
            logger.info("No card found for person ${person.id}")
        }
    }

    fun getFirstPersonWithCard(): Map<String, Any>? {
        return combinedDataChannel.tryReceive().getOrNull()
    }

    @PreDestroy
    fun cleanup() {
        runBlocking {
            coroutineScope.cancel()
            combinedDataChannel.consumeEach {
                forwardService.sendCombinedData(it["person"] as Person, it["card"] as Card)
            }
            combinedDataChannel.close()
        }
    }
}