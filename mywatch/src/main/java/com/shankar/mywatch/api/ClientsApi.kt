package com.shankar.clientmanagements.api

import com.shankar.clientmanagements.response.ClientsLoginResponse
import com.shankar.clientmanagements.response.DaysResponse
import retrofit2.Response
import retrofit2.http.*

interface ClientsApi {

    @FormUrlEncoded
    @POST("clients/login")
    suspend fun checkClients(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<ClientsLoginResponse>

    @GET("/clients/calculate-days")
    suspend fun loadDays(
        @Header("authorization") token: String,
    ): Response<DaysResponse>

}