package com.pandey.shubham.data

import io.ktor.http.*

class ResponseDto<T>(val status: HttpStatusCode, val message: String? = null, val data: T? = null)