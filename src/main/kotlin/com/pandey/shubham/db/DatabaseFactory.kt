package com.pandey.shubham.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import org.ktorm.database.Database

object DatabaseFactory {

    private const val HOST = "jdbc:mysql://localhost:3306"
    private const val DATABASE = "tictalk"
    private const val PASSWORD = "tictalk@db5678"
    lateinit var database: Database

   fun initDb() {
       val url = "jdbc:mysql://localhost:3306/$DATABASE?user=root&password=$PASSWORD&useSSL=false&jdbcCompliantTruncation=false"
       database = Database.connect(url)
   }

    fun initProdDb() {
        val url = "jdbc:mysql://tictalk-app:asia-south2:tictalk-app-prod:3306/$DATABASE?user=root&password=$PASSWORD&useSSL=false&jdbcCompliantTruncation=false"
        database = Database.connect(url)
    }

    fun init() {
//        Database.connect(hikari())
//        transaction {
//            SchemaUtils.create(User)
//        }
    }

    private fun hikari(): HikariDataSource {
        return HikariDataSource(HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
            username = "root"
            password = ""
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}