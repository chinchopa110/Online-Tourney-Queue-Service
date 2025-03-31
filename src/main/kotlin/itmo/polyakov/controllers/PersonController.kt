package itmo.polyakov.controllers

import itmo.polyakov.queueServices.PersonConsumerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/persons")
class PersonController(private val personConsumerService: PersonConsumerService) {

    @GetMapping
    fun getFirstPerson(): ResponseEntity<Map<String, Any>> {
        val person = personConsumerService.getFirstPerson()

        return if (person != null) {
            ResponseEntity.ok(mapOf(
                "status" to "success",
                "data" to person
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "status" to "empty",
                "message" to "Очередь пуста",
            ))
        }
    }
}