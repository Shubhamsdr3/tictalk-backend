package com.pandey.shubham.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
                call.respond(message = "Hi Shubham, how are you bro!")
            }
    }
}
