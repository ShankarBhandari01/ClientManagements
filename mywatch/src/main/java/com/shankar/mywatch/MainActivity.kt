package com.shankar.mywatch

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class MainActivity : WearableActivity() {
    private lateinit var userName: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login: Button
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding()
        setAmbientEnabled()

        login.setOnClickListener {
            UserLogin()
        }


    }


    private fun binding() {
        userName = findViewById(R.id.UserName)
        password = findViewById(R.id.Password)
        login = findViewById(R.id.Login)
        linearLayout = findViewById(R.id.snackbar_action)

    }


    private fun UserLogin() {

        if (userName.text.isNullOrEmpty()) {
            userName.error = "Please Enter UserName"
            userName.requestFocus()

        } else if (password.text.isNullOrEmpty()) {
            password.error = "Please Enter Password"
            password.requestFocus()
        } else {

            val username = userName.text.toString().trim()
            val password = password.text.toString().toLowerCase(Locale.ROOT).trim()
            CoroutineScope(Dispatchers.IO).launch {
                val repository = ClientsRepository()
                val response = repository.loginClients(username, password)
                if (response.success == true) {
                    saveUsernamePassword()
                    ServiceBuilder.token = "Bearer ${response.token}"
                    startActivity(
                        Intent(this@MainActivity, Dashboard::class.java)
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "${response.message}", Toast.LENGTH_SHORT)
                            .show()

                    }
                }


            }
        }


    }

    private fun saveUsernamePassword() {
        val username = userName.text.toString().trim()
        val password = password.text.toString().toLowerCase(Locale.ROOT).trim()
        val sharedPref = getSharedPreferences("UsernamePasswordPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
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
                            this@MainActivity, Dashboard::class.java
                        )
                    )
                    finish()
                } else {

                    startActivity(
                        Intent(
                            this@MainActivity, MainActivity::class.java
                        )
                    )

                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "${ex.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}