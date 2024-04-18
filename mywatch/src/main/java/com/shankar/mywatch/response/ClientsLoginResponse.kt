package com.shankar.clientmanagements.response

import android.os.Parcel
import android.os.Parcelable
import com.shankar.clientmanagements.entity.Clients

data class ClientsLoginResponse(
    val success: Boolean? = null,
    val token: String? = null,
    val message: String? = null,
    val details: MutableList<Clients>? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        TODO("details")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(success)
        parcel.writeString(token)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClientsLoginResponse> {
        override fun createFromParcel(parcel: Parcel): ClientsLoginResponse {
            return ClientsLoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<ClientsLoginResponse?> {
            return arrayOfNulls(size)
        }
    }
}