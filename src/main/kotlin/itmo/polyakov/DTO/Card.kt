package itmo.polyakov.DTO

data class Card(
    val id: String,
    val personId: String,
    val elo: Int,
    val federation: String,
    val createdAt: java.time.LocalDateTime = java.time.LocalDateTime.now()
)
