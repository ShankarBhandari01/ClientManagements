package com.shankar.clientmanagements.response

import com.shankar.clientmanagements.entity.User

data class UserLoginResponse(
    val success: Boolean? = null,
    val token: String? = null,
    val message: String? = null,
    val data: MutableList<User>?=null
)

