package com.pandey.shubham.data

import com.pandey.shubham.auth.JwtConfig
import com.pandey.shubham.db.DatabaseFactory
import com.pandey.shubham.jwtConfig
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class UserRepositoryImpl: UserRepository {

    override suspend fun addUser(userDto: UserDto): UserDto {
        val authToken = jwtConfig.generateToken(user = JwtConfig.JwtUser(userDto.name, userDto.phoneNumber))
        userDto.authToken = authToken
        DatabaseFactory.database.insert(User) {
            set(it.userId, userDto.userId)
            set(it.name, userDto.name)
            set(it.email, userDto.email)
            set(it.bio, userDto.bio)
            set(it.phoneNumber, userDto.phoneNumber)
            set(it.lastActive, userDto.lastActive)
            set(it.isOldUser, userDto.isOldUser)
            set(it.isOnline, userDto.isOnline)
            set(it.profileImage, userDto.profileImage)
            set(it.lastMessage, userDto.lastMessage)
            set(it.authToken, authToken)
        }
        return userDto
    }

    override suspend fun getUserById(userId: String): UserDto? {
        val userEntity = DatabaseFactory.database.sequenceOf(User).firstOrNull {
            it.userId eq userId
        }
        val referrerList = getReferrerByUserId(userId)
        val user = rowToUser(userEntity)
        user?.referrerId = referrerList
        return user
    }

    override suspend fun getAllUsers(): List<UserDto> {
        val c = ArrayList<UserDto>()
        val res = DatabaseFactory.database.sequenceOf(User).toList()
        res.map {
            c.add(
                UserDto(
                    userId = it.userId,
                    name = it.name,
                    email = it.email,
                    bio = it.bio,
                    phoneNumber = it.phoneNumber,
                    profileImage = it.profileImage,
                    isOldUser = it.isOldUser,
                    isOnline = it.isOnline,
                    lastActive = it.lastActive,
                    lastMessage = it.lastMessage
                )
            )
        }
        return c
    }

    override suspend fun deleteUser(userId: String): Boolean {
        val deletedRows = DatabaseFactory.database.delete(User) {
            it.userId eq  userId
        }
        if (deletedRows > 0) {
            return true
        }
        return false
    }

    override suspend fun updateUser(userDto: UserDto): UserDto? {
        val updatedRows = DatabaseFactory.database.update(User) {
            where { it.userId eq userDto.userId }
            set(it.userId, userDto.userId)
            set(it.name, userDto.name)
            set(it.email, userDto.email)
            set(it.bio, userDto.bio)
            set(it.phoneNumber, userDto.phoneNumber)
            set(it.lastActive, userDto.lastActive)
            set(it.isOldUser, userDto.isOldUser)
            set(it.isOnline, userDto.isOnline)
            set(it.profileImage, userDto.profileImage)
            set(it.lastMessage, userDto.lastMessage)
        }
        if (updatedRows > 0) return userDto
        return null
    }

    override suspend fun getUserByPhone(phoneNumber: String): UserDto? {
        val userEntity = DatabaseFactory.database.sequenceOf(User).firstOrNull { it.phoneNumber eq phoneNumber }
        return rowToUser(userEntity)
    }

    override suspend fun getReferrerByUserId(userId: String): List<String> {
        val query = DatabaseFactory.database.from(Referrer).select()
        val referrerList = mutableListOf<String>()
        query.map { row -> row[Referrer.referrerId]?.let { it -> referrerList.add(it) } }
        return referrerList
    }

    override suspend fun getUserProfile(userId: String): UserInfoResponse? {
        val userEntity = DatabaseFactory.database.sequenceOf(User).firstOrNull { it.userId eq userId }
        return rowToUserProfile(userEntity)
    }

    private fun rowToUserProfile(row: UserEntity?): UserInfoResponse? {
        if (row == null) { return null }
        val userInfoList = mutableListOf<UserInfoItem>()
        userInfoList.add(
            UserInfoItem(
                title = "Name",
                subtitle = row.name,
                image = null,
                description = "This is not your username or pin, This name will be visible in your contacts.",
                isEditable = true,
                position = 0
        ))
        userInfoList.add(
            UserInfoItem(
                title = "About",
                subtitle = row.bio,
                image = null,
                description = null,
                isEditable = true,
                position = 1
            ))
        userInfoList.add(
            UserInfoItem(
                title = "Phone",
                subtitle = row.phoneNumber,
                image = null,
                description = null,
                isEditable = false,
                position = 2
            ))
        return UserInfoResponse(
            profileImage = "https://www.whatsappimages.in/wp-content/uploads/2021/01/Boys-Feeling-Very-Sad-Images-Pics-Downlaod.jpg",
            userInfoList
        )
    }

    private fun rowToUser(row: UserEntity?): UserDto? {
        if (row == null) {
            return null
        }
        return UserDto(
            userId = row.userId,
            name = row.name,
            email = row.email,
            bio = row.bio,
            phoneNumber = row.phoneNumber,
            lastActive = row.lastActive,
            lastMessage = row.lastMessage,
            isOldUser = row.isOldUser,
            isOnline = row.isOnline,
            profileImage = row.profileImage,
        )
    }
}