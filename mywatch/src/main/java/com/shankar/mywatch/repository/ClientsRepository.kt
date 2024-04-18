package com.shankar.clientmanagements.repository

import com.shankar.clientmanagements.api.ClientsApi
import com.shankar.clientmanagements.api.MyApiRequest
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.entity.Clients
import com.shankar.clientmanagements.response.ClientsLoginResponse
import com.shankar.clientmanagements.response.DaysResponse


class ClientsRepository : MyApiRequest() {
    val myApi = ServiceBuilder.buildService(ClientsApi::class.java)
    suspend fun loginClients(username: String, password: String): ClientsLoginResponse {
        return apiRequest {
            myApi.checkClients(username, password)
        }
    }

    suspend fun getDays(): DaysResponse {
        return apiRequest {
            myApi.loadDays(ServiceBuilder.token!!)
        }
    }






}