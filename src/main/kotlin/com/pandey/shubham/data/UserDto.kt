package com.pandey.shubham.data

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val userId: String,
    val name: String,
    val email: String? = null,
    val bio: String? = null,
    val phoneNumber: String,
    val lastActive: String? = null,
    val isOldUser: Boolean? = null,
    val isOnline: Boolean? =  null,
    val profileImage: String? = null,
    val lastMessage: String? = null,
    var referrerId: List<String>? = null,
    var authToken: String? = null
)