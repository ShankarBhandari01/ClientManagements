package com.shankar.clientmanagements.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Offer_Table",indices = [Index(value = ["_id"],unique = true)])
class allpackage (
    val _id:String?=null,
    var title: String? = null,
    var price: String? = null,
    var image:String?=null,
    var description:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var OfferId: Int = 0
}

