package com.pandey.shubham.services

import com.google.gson.Gson
import com.pandey.shubham.auth.JwtConfig
import com.pandey.shubham.auth.LoginRequest
import com.pandey.shubham.data.*
import com.pandey.shubham.jwtConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

object UserService {

    private val userRepository = UserRepositoryImpl()

    fun Route.userRouting() {
        route("api") {
            get {
                call.respond("Hey, There Shubham this side.")
            }
            post("/user") {
                val user = call.receive<UserDto>()
                call.respondText(Gson().toJson(userRepository.addUser(user)), ContentType.Application.Json, HttpStatusCode.Created)
            }
            authenticate {
                get("/user/me") {
                    val user = call.authentication.principal as JwtConfig.JwtUser
                    call.respondText(Gson().toJson(user.toString()),ContentType.Application.Json)
                }
                get("/user") {
                    call.respondText(Gson().toJson(userRepository.getAllUsers()), ContentType.Application.Json)
                }
                get("/user/{id}") {
                    val userId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound)
                    call.respondText(Gson().toJson(userRepository.getUserById(userId)), ContentType.Application.Json)
                }
                delete("/user/{id}"){
                    val userId = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                    call.respondText(Gson().toJson(userRepository.deleteUser(userId)), ContentType.Application.Json)
                }
                put("/user/update") {
                    val user = call.receive<UserDto>()
                    call.respondText(Gson().toJson(userRepository.updateUser(user)), ContentType.Application.Json)
                }
            }
        }
    }
}