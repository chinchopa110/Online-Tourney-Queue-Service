package itmo.polyakov.DTO

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "card")
data class Card(
    @Id
    val id: String,

    @Column(name = "person_id", nullable = false)
    val personId: String,

    @Column(nullable = false)
    val elo: Int,

    @Column(nullable = false)
    val federation: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(
        id = "",
        personId = "",
        elo = 0,
        federation = "",
        createdAt = LocalDateTime.now()
    )
}