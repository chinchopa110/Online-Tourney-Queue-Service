package itmo.polyakov.controllers

import itmo.polyakov.services.PersonConsumerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/persons")
class PersonController(private val personConsumerService: PersonConsumerService) {

    @GetMapping
    fun getNextPersonInfo(): ResponseEntity<Map<String, Any>> {
        val info = personConsumerService.getFirstPersonWithCard()

        return if (info != null) {
            ResponseEntity.ok(mapOf(
                "status" to "success",
                "data" to info
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "status" to "empty",
                "message" to "Очередь пуста",
            ))
        }
    }
}