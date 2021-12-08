package com.pandey.shubham.data

interface UserRepository {

    suspend fun addUser(userDto: UserDto): UserDto?

    suspend fun getUserById(userId: String): UserDto?

    suspend fun getAllUsers(): List<UserDto>

    suspend fun deleteUser(userId: String): Boolean?

    suspend fun updateUser(userDto: UserDto): UserDto?

    suspend fun getUserByPhone(phoneNumber: String): UserDto?

    suspend fun getReferrerByUserId(userId: String): List<String>

    suspend fun getUserProfile(userId: String): UserInfoResponse?

    suspend fun getAllFriends(userId: String): List<UserDto>
}