package com.pandey.shubham.data

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val id: Int, val name: String, val email: String)