package com.shankar.clientmanagements.repository

import com.shankar.clientmanagements.api.MyApiRequest
import com.shankar.clientmanagements.api.OffersApi
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.response.AllOffersResponse

class OfferRepository : MyApiRequest() {
    val myApi =
        ServiceBuilder.buildService(OffersApi::class.java)


    suspend fun getOffers(): AllOffersResponse {
       return apiRequest {
        myApi.getOffers()
       }
    }

}

