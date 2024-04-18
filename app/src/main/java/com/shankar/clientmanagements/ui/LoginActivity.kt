package com.shankar.clientmanagements.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.biometrics.BiometricPrompt.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val Permission = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )


    private lateinit var chkRememberme: CheckBox
    private lateinit var userName: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login: Button
    private lateinit var linearLayout: LinearLayout


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding()
        checkRunTimePermission()
        login.setOnClickListener {
            UserLogin()
        }

    }





    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in Permission) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, Permission, 1)
    }

    private fun binding() {
        chkRememberme = findViewById(R.id.chkRememberme)
        userName = findViewById(R.id.UserName)
        password = findViewById(R.id.Password)
        login = findViewById(R.id.btn_Login)
        linearLayout = findViewById(R.id.snackbar_actionlogin)

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
                    RoomDataBase.getInstance(this@LoginActivity).getUserResponseDAO().deleteTables()
                    RoomDataBase.getInstance(this@LoginActivity).getUserResponseDAO()
                        .insertData(response.details!!)
                    ServiceBuilder.token = "Bearer ${response.token}"
                    if (chkRememberme.isChecked) {
                        saveUsernamePassword()
                    }
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            Dashboard::class.java
                        )
                    )

                    finish()
                } else {
                    withContext(Main) {
                        val snack =
                            Snackbar.make(
                                linearLayout,
                                "${response.message}",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("Ok", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()

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

}