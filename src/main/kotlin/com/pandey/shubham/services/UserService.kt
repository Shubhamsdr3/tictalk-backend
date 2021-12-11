package com.pandey.shubham.services

import com.google.gson.Gson
import com.pandey.shubham.auth.JwtConfig
import com.pandey.shubham.data.ResponseDto
import com.pandey.shubham.data.UserDto
import com.pandey.shubham.data.UserRepositoryImpl
import com.pandey.shubham.util.Constants
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

object UserService {

    fun Route.userRouting(jwtConfig: JwtConfig) {
        val userRepository = UserRepositoryImpl(jwtConfig)
        route("api") {
            get {
                call.respond(ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, "Hey, There Shubham this side."))
            }
            post("/user/login") {
                val user = call.receive<UserDto>()
                val responseDto = ResponseDto(HttpStatusCode.Created, Constants.SUCCESS, userRepository.addUser(user))
                call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
            }
            authenticate {
                get("/user/me") {
                    val user = call.authentication.principal as JwtConfig.JwtUser
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, user)
                    call.respondText(Gson().toJson(responseDto),ContentType.Application.Json)
                }
                get("/users") {
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, userRepository.getAllUsers())
                    call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
                }
                get("/user/{id}") {
                    val userId = call.parameters["id"] ?: return@get call.respond(ResponseDto(HttpStatusCode.NotFound, Constants.ERROR, null))
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, userRepository.getUserById(userId))
                    call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
                }
                delete("/user/{id}"){
                    val userId = call.parameters["id"] ?: return@delete call.respond(ResponseDto(HttpStatusCode.BadRequest, Constants.ERROR, null))
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, userRepository.deleteUser(userId))
                    call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
                }
                put("/user/update") {
                    val user = call.receive<UserDto>()
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, userRepository.updateUser(user))
                    call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
                }
                get("/user/{id}/profile") {
                    val userId = call.parameters["id"] ?: return@get call.respond(ResponseDto(HttpStatusCode.BadRequest, Constants.ERROR, null))
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, userRepository.getUserProfile(userId))
                    call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
                }

                get("/user/{id}/friends") {
                    val userId = call.parameters["id"] ?: return@get call.respond(ResponseDto(HttpStatusCode.NotFound, Constants.ERROR, null))
                    val responseDto = ResponseDto(HttpStatusCode.OK, Constants.SUCCESS, userRepository.getAllFriends(userId))
                    call.respondText(Gson().toJson(responseDto), ContentType.Application.Json)
                }
            }
        }
    }
}