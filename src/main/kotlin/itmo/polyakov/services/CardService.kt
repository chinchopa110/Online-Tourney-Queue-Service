package itmo.polyakov.services

import itmo.polyakov.DTO.Card
import itmo.polyakov.repositories.CardRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CardService(private val cardRepository: CardRepository) {

    @Transactional
    fun saveCard(card: Card): Card {
        return cardRepository.save(card)
    }

    fun getCardByPersonId(personId: String): Card? {
        return cardRepository.findByPersonId(personId)
    }

    fun getAllCards(): List<Card> {
        return cardRepository.findAll()
    }
}