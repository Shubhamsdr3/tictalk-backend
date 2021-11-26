package com.pandey.shubham.data

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(val profileImage: String?, val userInfo: List<UserInfoItem?>)

@Serializable
data class UserInfoItem(val title: String?, val subtitle: String?, val image: String?,val description: String?, val isEditable: Boolean, val position: Int)