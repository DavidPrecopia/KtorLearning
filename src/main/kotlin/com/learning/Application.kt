package com.learning

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
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
