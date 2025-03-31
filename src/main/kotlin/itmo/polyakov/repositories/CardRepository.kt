package itmo.polyakov.repositories

import itmo.polyakov.DTO.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : JpaRepository<Card, String> {
    fun findByPersonId(personId: String): Card?
}