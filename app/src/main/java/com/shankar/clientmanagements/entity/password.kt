package com.shankar.clientmanagements.entity

import android.os.Parcel
import android.os.Parcelable

class password(
    val password: String? = null,
    val NewPassword: String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(password)
        parcel.writeString(NewPassword)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<password> {
        override fun createFromParcel(parcel: Parcel): password {
            return password(parcel)
        }

        override fun newArray(size: Int): Array<password?> {
            return arrayOfNulls(size)
        }
    }
}