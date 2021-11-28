package com.pandey.shubham

import com.pandey.shubham.auth.JwtConfig
import com.pandey.shubham.auth.sendSMS
import com.pandey.shubham.services.UserService.userRouting
import com.twilio.Twilio
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

val jwtConfig = JwtConfig("2E3F09EA56DDD11318E367BB820B5693552298F853E3D32756F507A56F04D2CD")

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Location("/send")
class SendSMS

private const val ACCOUNT_SID = "ACa4d012de56fea30264899d78bbd141a7"
private const val AUTH_TOKEN = "88a2f9da18c17b964c1f070525c2bd3f"

fun Application.module(testing: Boolean = false) {
    val env = environment.config.propertyOrNull("ktor.development")?.getString()
    print("The dev mode is : $env")
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
        userRouting()
        sendSMS()
    }
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
}
