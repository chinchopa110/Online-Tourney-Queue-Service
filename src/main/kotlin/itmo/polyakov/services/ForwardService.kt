package itmo.polyakov.services

import itmo.polyakov.DTO.Card
import itmo.polyakov.DTO.Person
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ForwardService(private val kafkaTemplate: KafkaTemplate<String, Any>) {
    companion object {
        const val CARD_PERSON_TOPIC = "card-person-events"
    }

    fun sendCombinedData(person: Person, card: Card) {
        val combinedData = mapOf(
            "person" to person,
            "card" to card
        )
        kafkaTemplate.send(CARD_PERSON_TOPIC, person.id, combinedData)
    }
}

// TODO: подумать над outbox