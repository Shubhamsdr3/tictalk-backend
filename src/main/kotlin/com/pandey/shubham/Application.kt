package com.pandey.shubham

import com.pandey.shubham.auth.JwtConfig
import com.pandey.shubham.services.UserService.userRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json

val jwtConfig = JwtConfig("2E3F09EA56DDD11318E367BB820B5693552298F853E3D32756F507A56F04D2CD")

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    install(DefaultHeaders)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
    routing {
        userRouting()
    }
}
