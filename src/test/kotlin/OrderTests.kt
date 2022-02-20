import com.learning.module
import com.learning.routes.ORDERS_PATH_BASE
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class OrderRoutesTest {
    @Test
    fun testGetOrder() {
        //this function indicates that we want to run the Application as a test.
        //invoking the module fun in Application.kt
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
}