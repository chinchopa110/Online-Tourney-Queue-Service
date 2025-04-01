package itmo.polyakov

import io.mockk.coEvery
import io.mockk.mockk
import itmo.polyakov.DTO.Card
import itmo.polyakov.DTO.Person
import itmo.polyakov.controllers.PersonController
import itmo.polyakov.services.PersonConsumerService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class PersonControllerTest {

    private val mockPersonService = mockk<PersonConsumerService>()
    private val controller = PersonController(mockPersonService)

    private val testPerson = Person(id = "1", name = "Test User", age = 30, email = "test@test.ru")
    private val testCard = Card(id = "card1", personId = "1", elo = 1000, federation = "Russia")
    private val testData = mapOf(
        "person" to testPerson,
        "card" to testCard
    )

    @Test
    fun `getFirstPersonWithCard returns person with card when queue is not empty`() = runBlocking {
        coEvery { mockPersonService.getFirstPersonWithCard() } returns testData

        val response = controller.getNextPersonInfo()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals("success", response.body?.get("status"))
        assertEquals(testData, response.body?.get("data"))
    }

    @Test
    fun `getFirstPersonWithCard returns empty message when queue is empty`() = runBlocking {
        coEvery { mockPersonService.getFirstPersonWithCard() } returns null

        val response = controller.getNextPersonInfo()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("empty", response.body?.get("status"))
        assertEquals("Очередь пуста", response.body?.get("message"))
    }
}