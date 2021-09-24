package com.pandey.shubham.data

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object User : Table("user") {
    val userId = integer("user_id")
    val name = varchar("name", length = 50)
    val email = varchar("emaild", length = 50)
}

fun initDB() {

    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(User)

        User.insert {
            it[userId] = 1
            it[name] = "Andrey"
            it[email] = "city"
        }

        User.insert {
            it[userId] = 2
            it[name] = "Sergey"
            it[email] = "city"
        }

        User.insert {
            it[userId] = 3
            it[name] = "Eugene"
            it[email] = "city"
        }

        User.insert {
            it[userId] = 4
            it[name] = "Alex"
            it[email] = "city"
        }

        User.insert {
            it[userId] = 5
            it[name] = "Something"
            it[email] = "city"
        }
    }
}