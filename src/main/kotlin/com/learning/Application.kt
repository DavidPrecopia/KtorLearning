package com.learning

import com.learning.routes.registerCustomerRoutes
import com.learning.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

//I guess we'll use the argument at a later time. Maybe need to modify our init if under test?
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
    }

    //invokes the extension function in their respective Kt files
    registerCustomerRoutes()
    registerOrderRoutes()
}


// Simpler embedded server - now using application.conf, so need EngineMain
//fun main() {
//    embeddedServer(Netty, port = 8080) {
//        routing {
//            get("/") {
//                call.respondText("Hello, world!")
//            }
//            get("/norsk") {
//                call.respondText("Hallo og god morgen!")
//            }
//        }
//    }.start(wait = true)
//}
