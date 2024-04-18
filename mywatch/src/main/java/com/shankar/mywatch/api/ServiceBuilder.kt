package com.shankar.clientmanagements.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "http://10.0.2.2:90/"
    //private const val BASE_URL = "http://192.168.10.72/"
    var token: String? = null
    private val okHttp = OkHttpClient.Builder()
    private val retroBuilder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // create retrofit instance
    private val retrofit = retroBuilder.build()
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

   // fun loadImagePath(): String {
     //   val arr = BASE_URL.split("/").toTypedArray()
     //   return arr[0] + "/" + arr[1] + arr[2] + "/pictures/"
   // }
    fun loadImagePath(): String {
       return  BASE_URL
    }
}