package com.learning

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
            get("/norsk") {
                call.respondText("Hallo og god morgen!")
            }
        }
    }.start(wait = true)
}
