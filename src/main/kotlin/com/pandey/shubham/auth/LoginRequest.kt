package com.pandey.shubham.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val phoneNumber: String, val message: String)