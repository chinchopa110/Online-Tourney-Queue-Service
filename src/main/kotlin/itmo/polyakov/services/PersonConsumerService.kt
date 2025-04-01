package itmo.polyakov.services

import itmo.polyakov.DTO.Person
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Service
class PersonConsumerService(
    private val cardService: CardService,
    private val forwardService: ForwardService
) {
    private val combinedDataQueue: BlockingQueue<Map<String, Any>> = LinkedBlockingQueue()

    @KafkaListener(topics = ["queue"], groupId = "person-consumer-group")
    fun consume(person: Person) {
        processPersonWithCard(person)
    }

    private fun processPersonWithCard(person: Person) {
        val card = cardService.getCardByPersonId(person.id)
        card?.let {
            val combinedData = mapOf(
                "person" to person,
                "card" to it
            )
            combinedDataQueue.put(combinedData)
            forwardService.sendCombinedData(person, it)
        }
    }

    fun getFirstPersonWithCard(): Map<String, Any>? {
        return combinedDataQueue.poll()
    }
}