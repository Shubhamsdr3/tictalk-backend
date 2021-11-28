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
    private const val PASSWORD = ""
//    val database: Database by lazy { Database.connect(hikari()) }
    val database: Database by lazy { initDb() }

   fun initDb(): Database {
       val url = "jdbc:mysql://localhost:3306/$DATABASE?user=root&password=$PASSWORD&useSSL=false&jdbcCompliantTruncation=false"
       return Database.connect(url)
   }

    private fun hikari(): HikariDataSource {
        return HikariDataSource(HikariConfig().apply {
            jdbcUrl = String.format("jdbc:mysql:///%s", DATABASE)
            username = "admin-shubham"
            password = "tictalk@db5678"
            addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory")
            addDataSourceProperty("cloudSqlInstance", "tictalk-app:asia-south2:tictalk-app-prod")
            addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
            maximumPoolSize = 3
            isAutoCommit = true
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}