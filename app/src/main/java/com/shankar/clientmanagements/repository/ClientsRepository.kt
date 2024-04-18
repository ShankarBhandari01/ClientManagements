package com.shankar.clientmanagements.repository

import com.shankar.clientmanagements.api.ClientsApi
import com.shankar.clientmanagements.api.MyApiRequest
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.entity.Clients
import com.shankar.clientmanagements.entity.password
import com.shankar.clientmanagements.response.*
import okhttp3.MultipartBody

class ClientsRepository : MyApiRequest() {
    val myClientsApi = ServiceBuilder.buildService(ClientsApi::class.java)
    suspend fun loginClients(username: String, password: String): ClientsLoginResponse {
        return apiRequest {
            myClientsApi.checkClients(username, password)
        }
    }

    suspend fun getDays(): DaysResponse {
        return apiRequest {
            myClientsApi.loadDays(ServiceBuilder.token!!)
        }
    }

    suspend fun uploadImage(body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            myClientsApi.uploadImage(ServiceBuilder.token!!, body)
        }
    }

    suspend fun update(id: String, clients: Clients): UpdateResponse {
        return apiRequest {
            myClientsApi.update(
                ServiceBuilder.token!!, id, clients
            )
        }
    }

    suspend fun updatePassword(password: password): UpdateResponse {
        return apiRequest {
            myClientsApi.ChangePassword(
                ServiceBuilder.token!!, password
            )
        }
    }

    suspend fun UpdateResub(
        subscriptionTO: String,
        subscriptionType: String
    ): UpdateResponse {
        return apiRequest {
            myClientsApi.Resubscription(
                ServiceBuilder.token!!,
                subscriptionType,
                subscriptionTO
            )
        }
    }

    suspend fun getRequest(): RequestResponse {
        return apiRequest {
            myClientsApi.loadRequest(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteRequest(id: String): UpdateResponse {
        return apiRequest {
            myClientsApi.deleteRequest(ServiceBuilder.token!!, id)
        }
    }
}