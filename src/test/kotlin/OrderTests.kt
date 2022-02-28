import com.learning.module
import com.learning.routes.ORDERS_PATH_BASE
import com.learning.routes.ORDERS_TOTAL
import com.learning.routes.ORDER_CREATED
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class OrderRoutesTest {
    @Test
    fun getAllOrders() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, ORDERS_PATH_BASE).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(
                    """[{"orderNumber":"2022","orderedItems":[{"name":"6700 XT","price":950.0,"quantity":1},{"name":"5600x","price":279.99,"quantity":1}]},{"orderNumber":"2020-04-06-01","orderedItems":[{"name":"ASUS ROG Desk Pad","price":35.95,"quantity":1},{"name":"Apple Pencil","price":105.99,"quantity":2}]},{"orderNumber":"2020-04-03-01","orderedItems":[{"name":"Aquafina 32pk","price":5.64,"quantity":10},{"name":"Mint Ice Cream","price":8.0,"quantity":2},{"name":"Pizza 3pk","price":12.9,"quantity":4}]}]""",
                    response.content
                )
            }
        }
    }

    @Test
    fun getOrderById() {
        /**
         * This sets-up the test application - using Application's [module] to do so.
         * this function indicates that we want to run the Application as a test.
         */
        withTestApplication({ module(testing = true) }) {
            //this function will handle the request for us (as its name indicates), thus we need
            // to specify the method and path.
            handleRequest(HttpMethod.Get, "$ORDERS_PATH_BASE/2020-04-06-01").apply {
                assertEquals(
                    //the triple quotes is a raw String, saves you from having to escape every
                    // quote in the JSON
                    """{"orderNumber":"2020-04-06-01","orderedItems":[{"name":"ASUS ROG Desk Pad","price":35.95,"quantity":1},{"name":"Apple Pencil","price":105.99,"quantity":2}]}""",
                    response.content
                )
            }
        }
    }

    @Test
    fun postOrder() {
        val orderNumberItself = "2022-02-21-17-12"
        val orderedItems = "[{\"name\":\"6700 XT\",\"price\":950.0,\"quantity\":1},{\"name\":\"5600x\",\"price\":279.99,\"quantity\":1}]"

        //POST order
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, ORDERS_PATH_BASE) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                //this creates a List of Pairs
                //formUrlEncode formats the List of Pairs to the HTML format.
                setBody("""{"orderNumber":"$orderNumberItself","orderedItems":$orderedItems}""")
//                setBody(listOf("orderNumber" to orderNumberItself, "orderedItems" to orderedItemsTest).formUrlEncode())
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals(ORDER_CREATED, response.content)
            }

            //GET order
            handleRequest(HttpMethod.Get, "$ORDERS_PATH_BASE/$orderNumberItself").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(
                    """{"orderNumber":"$orderNumberItself","orderedItems":$orderedItems}""",
                    response.content
                )
            }
        }
    }

    @Test
    fun getOrderTotal() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "$ORDERS_PATH_BASE/2022$ORDERS_TOTAL").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("1229.99", response.content)
            }
        }
    }
}