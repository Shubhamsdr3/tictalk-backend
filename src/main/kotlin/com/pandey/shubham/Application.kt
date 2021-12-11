package com.pandey.shubham

import com.pandey.shubham.auth.JwtConfig
import com.pandey.shubham.data.UserRepositoryImpl
import com.pandey.shubham.db.DatabaseInfo
import com.pandey.shubham.services.UserService.userRouting
import com.pandey.shubham.util.getLogger
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val logger = getLogger<Application>()
    val jwtSecret = environment.config.property("ktor.jwt.privateKey").getString()
    DatabaseInfo.userName = environment.config.property("ktor.db.userName").getString()
    DatabaseInfo.password = environment.config.property("ktor.db.password").getString()
    val jwtConfig = JwtConfig(jwtSecret)
    install(CallLogging)
    install(DefaultHeaders)
    install(Locations)
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
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    install(Routing) {
        userRouting(jwtConfig)
    }

    environment.monitor.subscribe(ApplicationStarted) {
        logger.info("Application has started...")
    }

    environment.monitor.subscribe(ApplicationStopped) {
        DatabaseInfo.clearData()
    }
}
