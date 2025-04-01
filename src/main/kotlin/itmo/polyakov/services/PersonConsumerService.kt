package itmo.polyakov.services

import itmo.polyakov.DTO.Person
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class PersonConsumerService(
    private val cardService: CardService,
    private val forwardService: ForwardService
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val combinedDataChannel = Channel<Map<String, Any>>(capacity = Channel.UNLIMITED)

    @KafkaListener(topics = ["queue"], groupId = "person-consumer-group")
    fun consume(person: Person) {
        coroutineScope.launch {
            try {
                processPersonWithCard(person)
            } catch (e: Exception) {
                println("Error processing person ${person.id}: ${e.message}")
            }
        }
    }

    private suspend fun processPersonWithCard(person: Person) {
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
            println("No card found for person ${person.id}")
        }
    }

    fun getFirstPersonWithCard(): Map<String, Any>? {
        return combinedDataChannel.tryReceive().getOrNull()
    }

    @PreDestroy
    fun cleanup() {
        coroutineScope.cancel()
        combinedDataChannel.close()
    }
}