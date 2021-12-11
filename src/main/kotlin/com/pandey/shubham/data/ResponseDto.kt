package com.pandey.shubham.data

import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(@Contextual val status: HttpStatusCode, val message: String? = null, val data: T? = null)