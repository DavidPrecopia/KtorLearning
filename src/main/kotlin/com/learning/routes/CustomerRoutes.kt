package com.learning.routes

import com.learning.model.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

//this is an extension function - I'm creating a function in the Route class.
//I'm then invoking the route function to instantiate the route that will be used my the client.
fun Route.customerRouting() {
    route("/customer") {
        get {
                if (customerStorage.isEmpty())
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            else
                call.respond(customerStorage)
        }
        get("{id}") {

        }
        post {

        }
        delete("{id}") {

        }
    }
}