package itmo.polyakov.queueServices

import itmo.polyakov.DTO.Person
import itmo.polyakov.services.ForwardService
import itmo.polyakov.services.CardService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Service
class PersonConsumerService(
    private val cardService: CardService,
    private val forwardService: ForwardService
) {
    private val personQueue: BlockingQueue<Person> = LinkedBlockingQueue()

    @KafkaListener(topics = ["queue"], groupId = "person-consumer-group")
    fun consume(person: Person) {
        personQueue.put(person)
        processPersonWithCard(person)
    }

    private fun processPersonWithCard(person: Person) {
        val card = cardService.getCardByPersonId(person.id)
        card?.let {
            forwardService.sendCombinedData(person, it)
        }
    }

    fun getFirstPerson(): Person? {
        return personQueue.poll()
    }
}

// TODO: inbox??