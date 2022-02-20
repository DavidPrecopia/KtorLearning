package com.learning.routes

import com.learning.model.Customer
import com.learning.model.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

//registering all the below defined routes
fun Application.registerCustomerRoutes() {
    routing {
        customerRouting()
    }
}

//this is an extension function - I'm creating a function in the Route class.
//I'm then invoking the route function to instantiate the route that will be used my the client.
fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isEmpty())
                call.respondText(NO_CUSTOMERS, status = HttpStatusCode.NotFound)
            else
                call.respond(customerStorage)
        }
        get("{id}") {
            //the `return@get` is specifying to return from the sub-function `get`, not the entire function.
            val id = call.parameters["id"] ?: return@get call.respondText(
                ID_MISSING_MALFORMED,
                status = HttpStatusCode.NotFound
            )

            //if the ID is not null, attempt to find it in our Customer Db.
            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                CUSTOMER_NOT_FOUND + id,
                status = HttpStatusCode.NotFound
            )

            //finally, return the requested Customer.
            call.respond(customer)
        }
        // POST == add
        post {
            //parse the received JSON into the Customer data class
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText(
                CUSTOMER_STORED,
                status = HttpStatusCode.Created //201
            )
        }
        delete("{id}") {
            //similar to the GET ID path
            val id = call.parameters["id"] ?: return@delete call.respondText(
                ID_MISSING_MALFORMED, status = HttpStatusCode.BadRequest
            )

            //differing from GET ID by using an if-else statement.
            if (customerStorage.removeIf { it.id == id })
                call.respondText(CUSTOMER_DELETED, status = HttpStatusCode.Accepted)
            else
                call.respondText(CUSTOMER_NOT_FOUND, status = HttpStatusCode.NotFound)
        }
    }
}