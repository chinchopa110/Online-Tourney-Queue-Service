package itmo.polyakov

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PersonQueueApplication

fun main(args: Array<String>) {
    runApplication<PersonQueueApplication>(*args)
}