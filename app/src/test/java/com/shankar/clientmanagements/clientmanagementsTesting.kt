package com.shankar.clientmanagements

import com.shankar.clientmanagements.repository.ClientsRepository
import com.shankar.clientmanagements.repository.OfferRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class clientmanagementsTesting {
    @Test
    fun testLogin() = runBlocking {
        val repository = ClientsRepository()
        val response = repository.loginClients("shankar01", "123456")
        val expected = true
        val acutalResults = response.success
        Assert.assertEquals(expected, acutalResults)
    }

    @Test
    fun getDaysTesting() = runBlocking {
        val repository = ClientsRepository()
        val LOgin = repository.loginClients("shankar01", "123456")
        val token = "Bearer ${LOgin.token}"
        val response = repository.myClientsApi.loadDays(token!!)
        val expected = true
        val acutalResults = response.isSuccessful
        Assert.assertEquals(expected, acutalResults)
    }

    @Test
    fun getOfferTesting() = runBlocking {
        val repository = OfferRepository()
        val response = repository.getOffers()
        val expected = true
        val acutalResults = response.success
        Assert.assertEquals(expected, acutalResults)
    }

    @Test
    fun getRequestTesting() = runBlocking {
        val repository = ClientsRepository()
        val Login = repository.loginClients("shankar01", "123456")
        val token = "Bearer ${Login.token}"
        val response = repository.myClientsApi.loadRequest(token)
        val expected = true
        val acutalResults = response.isSuccessful
        Assert.assertEquals(expected, acutalResults)
    }

    @Test
    fun updateResubTesting() = runBlocking {
        val repository = ClientsRepository()
        val Login = repository.loginClients("shankar01", "123456")
        val token = "Bearer ${Login.token}"
        val response =
            repository.myClientsApi.Resubscription(token, "Internet and Cable", "2020-08-01")
        val expected = true
        val acutalResults = response.isSuccessful
        Assert.assertEquals(expected, acutalResults)
    }

    @Test
    fun DeleteRequestTesting() = runBlocking {
        val repository = ClientsRepository()
        val Login = repository.loginClients("shankar01", "123456")
        val token = "Bearer ${Login.token}"
        val response = repository.myClientsApi.deleteRequest(token, "607be5f5b13d1c46a037e96d")
        val expected = true
        val acutalResults = response.isSuccessful
        Assert.assertEquals(expected, acutalResults)
    }

}