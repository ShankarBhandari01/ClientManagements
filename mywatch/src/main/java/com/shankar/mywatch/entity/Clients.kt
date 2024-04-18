package com.shankar.clientmanagements.entity

import android.os.Parcel
import android.os.Parcelable



data class Clients(
    val _id: String? = null,
    val image: String? = null,
    val citizenshipNumber: String? = null,
    val subscriptionType: String? = null,
    val subscriptionDate: String? = null,
    val subscriptionTO: String? = null,
    val full_name: String? = null,
    val Dob: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val contact: String? = null,
    val username: String? = null,
    val password: String? = null,
    val CreateDate: String? = null,
    val usertype: String?=null
)