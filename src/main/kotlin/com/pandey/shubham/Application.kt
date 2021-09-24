package com.pandey.shubham

import com.google.gson.Gson
import com.pandey.shubham.data.User
import com.pandey.shubham.data.UserDto
import com.pandey.shubham.data.initDB
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    initDB()
    val server = embeddedServer(Netty, port = 8888) {
        routing {
            userRouting()
        }
    }
    server.start(wait = true)
}

fun Application.module() {
    initDB()
    install(ContentNegotiation) {
        json()
    }
}

fun Route.userRouting() {
    route("api") {
        get("/user"){
            call.respondText(getTopUserData(), ContentType.Application.Json)
        }
        get("/user/{id}") {
            val userId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound)
            getUser(userId = userId.toInt())
        }
        post {
            val user = call.receive<UserDto>()
            saveUser(user)
            call.respond(HttpStatusCode.Created)
        }
        delete("/{id}"){
            val userId = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            call.respondText(deleteUser(userId.toInt()), ContentType.Application.Json)
        }
    }

}


fun getTopUserData(): String {
    var json = ""
    transaction {
        val res = User.selectAll().orderBy(User.userId, false).limit(5)
        val c = ArrayList<UserDto>()
        for (f in res) {
            c.add(UserDto(id = f[User.userId], name = f[User.name], email = f[User.email]))
        }
        json = Gson().toJson(c)
    }
    return json
}

fun getUser(userId: Int): String {
    var json = ""
    transaction {
//        val res = User.select(where = User.userId.equals(userId))
//        json = Gson().toJson(res)
    }
    return json
}

fun saveUser(userDto: UserDto) {
    transaction {
        User.insert {
            it[userId] = userDto.id
            it[name] = userDto.name
            it[email] = userDto.email
        }
    }
}

fun deleteUser(id : Int): String {
    var count = 0;
    transaction {
        count = User.deleteWhere { User.userId eq id }
    }
    return if(count > 0)
        "true"
    else
        "false"
}