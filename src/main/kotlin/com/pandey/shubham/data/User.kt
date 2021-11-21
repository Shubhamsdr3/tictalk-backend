package com.pandey.shubham.data

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.varchar

object User : Table<UserEntity>("user") {
    val userId = varchar("userId").primaryKey().bindTo { it.userId }
    val name = varchar("name").bindTo { it.name }
    val email = varchar("email").bindTo { it.email }
    val bio = varchar("bio").bindTo { it.bio }
    val phoneNumber = varchar("phoneNumber").bindTo { it.phoneNumber }
    val lastActive = varchar("lastActive").bindTo { it.lastActive }
    val isOldUser = boolean("isOldUser").bindTo { it.isOldUser }
    val isOnline = boolean("isOnline").bindTo { it.isOnline }
    val profileImage = varchar("profileImage").bindTo { it.profileImage }
    val lastMessage = varchar("lastMessage").bindTo { it.lastMessage }
    val authToken = varchar("authToken").bindTo { it.authToken }
}

interface UserEntity : Entity<UserEntity> {
    companion object: Entity.Factory<UserEntity>()
    val userId : String
    val name : String
    val email : String
    val bio : String
    val phoneNumber : String
    val lastActive : String
    val isOldUser : Boolean
    val isOnline : Boolean
    val profileImage : String
    val lastMessage : String
    val authToken: String
}

