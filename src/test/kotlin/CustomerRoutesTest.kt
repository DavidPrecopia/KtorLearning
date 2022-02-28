import com.learning.module
import com.learning.routes.CUSTOMER_CREATED
import com.learning.routes.CUSTOMER_DELETED
import com.learning.routes.CUSTOMER_PATH_BASE
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CustomerRoutesTest {

    private var justAddedCustomerId = "0";

    @After
    fun afterEachTest() {
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Delete, "$CUSTOMER_PATH_BASE/$justAddedCustomerId").apply {
                assertEquals(HttpStatusCode.Accepted, response.status())
                assertEquals(CUSTOMER_DELETED, response.content)
            }
        }
    }

    @Test
    fun getAllCustomers() {
        val customerId = "0101"
        val customerJson = "{\"id\":\"$customerId\",\"firstName\":\"David\",\"lastName\":\"Precopia\",\"email\":\"fake@apple.com\"}"
        justAddedCustomerId = customerId

        //POST order
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, CUSTOMER_PATH_BASE) {
                //specifying the type of content that is being sent.
                //the latter arg needs `toString` due to it being a ContentType
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                //this creates a List of Pairs
                //formUrlEncode formats the List of Pairs to the HTML format.
                setBody(customerJson)
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals(CUSTOMER_CREATED, response.content)
            }

            //GET all customers
            handleRequest(HttpMethod.Get, CUSTOMER_PATH_BASE).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[$customerJson]", response.content)
            }
        }
    }

    @Test
    fun getCustomerById() {
        val customerId = "02"
        val customerJson = "{\"id\":\"$customerId\",\"firstName\":\"David\",\"lastName\":\"Precopia\",\"email\":\"fake@apple.com\"}"
        justAddedCustomerId = customerId

        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, CUSTOMER_PATH_BASE) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(customerJson)
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals(CUSTOMER_CREATED, response.content)
            }

            handleRequest(HttpMethod.Get, "$CUSTOMER_PATH_BASE/$customerId").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(customerJson, response.content)
            }
        }
    }
}