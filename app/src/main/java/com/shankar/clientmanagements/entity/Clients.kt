package com.shankar.clientmanagements.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "User_Table", indices = [Index(value = ["username"], unique = true)])
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
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        userId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(image)
        parcel.writeString(citizenshipNumber)
        parcel.writeString(subscriptionType)
        parcel.writeString(subscriptionDate)
        parcel.writeString(subscriptionTO)
        parcel.writeString(full_name)
        parcel.writeString(Dob)
        parcel.writeString(gender)
        parcel.writeString(address)
        parcel.writeString(contact)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(CreateDate)
        parcel.writeString(usertype)
        parcel.writeInt(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clients> {
        override fun createFromParcel(parcel: Parcel): Clients {
            return Clients(parcel)
        }

        override fun newArray(size: Int): Array<Clients?> {
            return arrayOfNulls(size)
        }
    }


}