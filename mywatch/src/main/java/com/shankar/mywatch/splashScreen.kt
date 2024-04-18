package com.shankar.mywatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        checkUser()
    }


    private fun checkUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = ClientsRepository()
                val sharedPref = getSharedPreferences("UsernamePasswordPref", MODE_PRIVATE)
                val username = sharedPref.getString("username", " ").toString()
                val password = sharedPref.getString("password", " ").toString()
                val response = repository.loginClients(username, password)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    startActivity(
                        Intent(
                            this@splashScreen, Dashboard::class.java
                        )
                    )
                    finish()
                } else {

                    startActivity(
                        Intent(
                            this@splashScreen,MainActivity::class.java
                        )
                    )
                    finish()

                }
            } catch (ex: IOException) {
                withContext(Main){
                    Toast.makeText(this@splashScreen, "${ex.localizedMessage}", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}