package itmo.polyakov.queueServices

import itmo.polyakov.DTO.Person
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Service
class PersonConsumerService {
    private val personQueue: BlockingQueue<Person> = LinkedBlockingQueue()

    @KafkaListener(topics = ["queue"], groupId = "person-consumer-group")
    fun consume(person: Person) {
        personQueue.put(person)
    }

    fun getFirstPerson(): Person? {
        return personQueue.poll()
    }
}