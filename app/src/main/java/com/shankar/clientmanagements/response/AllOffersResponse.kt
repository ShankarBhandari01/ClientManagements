package com.shankar.clientmanagements.response


import com.shankar.clientmanagements.entity.allpackage


data class AllOffersResponse(
    val success:Boolean?=null,
    val allpackage: MutableList<allpackage>? = null
)
