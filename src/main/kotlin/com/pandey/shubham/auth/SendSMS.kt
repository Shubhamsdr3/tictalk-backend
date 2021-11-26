package com.pandey.shubham.auth

import com.google.gson.Gson
import com.pandey.shubham.SendSMS
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

private const val TWILIO_NUMBER = "881585678"

fun Route.sendSMS() {
    post<SendSMS> {
        try {
            run {
                val request = call.receive<LoginRequest>()
                val message = sendMessage(request.phoneNumber, request.message)
                call.respond(LoginResponse(true, message.sid))
            }
        } catch (e: Exception) {
//            App.logger.error(call.request.logInfo(), e)
            call.respondText(
                Gson().toJson(LoginResponse(false, e.localizedMessage)),
                ContentType.Application.Json,
                HttpStatusCode.BadRequest
            )
        }
    }
}

private fun sendMessage(phoneNumber: String, message: String): Message {
    return Message.creator(PhoneNumber(phoneNumber), PhoneNumber(TWILIO_NUMBER), message).create()
}