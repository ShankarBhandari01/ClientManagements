package com.shankar.clientmanagements.api

import com.shankar.clientmanagements.entity.Clients
import com.shankar.clientmanagements.entity.password
import com.shankar.clientmanagements.entity.resubs
import com.shankar.clientmanagements.response.*
import okhttp3.MultipartBody
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


    @Multipart
    @PUT("/profile")
    suspend fun uploadImage(
        @Header("authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    @PUT("/clients/update/{id}")
    suspend fun update(
        @Header("authorization") token: String,
        @Path("id") id: String,
        @Body clients: Clients
    ): Response<UpdateResponse>

    @PUT("/password")
    suspend fun ChangePassword(
        @Header("authorization") token: String,
        @Body password: password
    ): Response<UpdateResponse>


    @FormUrlEncoded
    @POST("clients/resub")
    suspend fun Resubscription(
        @Header("authorization") token: String,
        @Field("title") subscriptionType: String,
        @Field("description") subscriptionTO: String
    ): Response<UpdateResponse>



    @GET("/all/request")
    suspend fun loadRequest(
        @Header("authorization") token: String,
    ): Response<RequestResponse>


    @DELETE("/request/delete/{id}")
    suspend fun deleteRequest(
        @Header("authorization") token: String,
        @Path("id") id: String
    ):Response<UpdateResponse>
}