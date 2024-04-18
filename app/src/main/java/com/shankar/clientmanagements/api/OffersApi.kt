package com.shankar.clientmanagements.api

import com.shankar.clientmanagements.response.AllOffersResponse
import com.shankar.clientmanagements.response.ClientsLoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header

interface OffersApi {
    @GET("/getAllPackage")
    suspend fun getOffers(): Response<AllOffersResponse>
}