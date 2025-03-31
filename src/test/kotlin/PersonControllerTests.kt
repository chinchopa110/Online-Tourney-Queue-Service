package itmo.polyakov.controllers

import io.mockk.every
import io.mockk.mockk
import itmo.polyakov.DTO.Person
import itmo.polyakov.queueServices.PersonConsumerService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class PersonControllerTest {

    private val mockPersonService = mockk<PersonConsumerService>()
    private val controller = PersonController(mockPersonService)

    private val testPerson = Person(id = "1", name = "Test User", age = 30, email = "test@test.ru")

    @Test
    fun `getFirstPerson returns person when queue is not empty`() {
        every { mockPersonService.getFirstPerson() } returns testPerson

        val response = controller.getFirstPerson()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals("success", response.body?.get("status"))
        assertEquals(testPerson, response.body?.get("data"))
    }

    @Test
    fun `getFirstPerson returns empty message when queue is empty`() {
        every { mockPersonService.getFirstPerson() } returns null
        val response = controller.getFirstPerson()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("empty", response.body?.get("status"))
        assertEquals("Очередь пуста", response.body?.get("message"))
    }
}