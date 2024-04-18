package com.shankar.clientmanagements.response

import com.shankar.clientmanagements.entity.User
import com.shankar.clientmanagements.entity.resubs

class RequestResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val allReq: MutableList<resubs>?=null
)