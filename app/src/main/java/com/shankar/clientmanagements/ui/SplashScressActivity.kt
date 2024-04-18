package com.shankar.clientmanagements.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SplashScressActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var Lightsensor: Sensor? = null
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scress)
        linearLayout = findViewById(R.id.snackbar_actionSp)
        refresh = findViewById(R.id.refresh)
        refresh.setOnRefreshListener {
            checkConnectivity()
            refresh.isRefreshing = false
        }
        checkConnectivity()
        Lightsensors()
    }

    private fun checkConnectivity() {
        val connection: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activieNetwork: NetworkInfo? = connection.activeNetworkInfo
        val isConnected: Boolean = activieNetwork?.isConnectedOrConnecting == true
        if (!isConnected) {
            val snack =
                Snackbar.make(
                    linearLayout,
                    "Internet Not Connected",
                    Snackbar.LENGTH_LONG
                )
            snack.setAction("Ok", View.OnClickListener {
                snack.dismiss()
            })
            snack.show()
        } else {
            checkUser()
        }
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
                    RoomDataBase.getInstance(this@SplashScressActivity).getUserResponseDAO()
                        .insertData(response.details!!)
                    ServiceBuilder.token = "Bearer ${response.token}"
                    startActivity(
                        Intent(
                            this@SplashScressActivity, Dashboard::class.java
                        )
                    )
                    finish()
                } else {

                    startActivity(
                        Intent(
                            this@SplashScressActivity, LoginActivity::class.java
                        )
                    )
                    finish()

                }
            } catch (ex: IOException) {
                withContext(Main) {
                    val snack =
                        Snackbar.make(
                            linearLayout,
                            ex.localizedMessage,
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

    fun Lightsensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor())
            return
        else {
            Lightsensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, Lightsensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
            flag = false
        } else {
            flag
        }
        return flag
    }


    override fun onSensorChanged(event: SensorEvent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val values = event!!.values[0]
            if (values >= 70) {
                withContext(Main) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else if (values <= 20) {
                withContext(Main) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }


}
