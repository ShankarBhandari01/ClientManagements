package com.shankar.clientmanagements.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shankar.clientmanagements.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Dashboard : AppCompatActivity(), SensorEventListener {
    private lateinit var nav_host_fragment: FrameLayout
    private lateinit var navView: BottomNavigationView
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var Psensor: Sensor? = null
    lateinit var vibrator: Vibrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_profile,
            )
        )

        Proximitysensors()
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_Logout -> logout()
            R.id.nav_About -> map()
            R.id.nav_Setting -> setting()
            R.id.nav_Subs -> resubscription()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resubscription() {
        startActivity(
            Intent(
                this,
                ResubscriptionActivity::class.java
            )
        )
    }

    fun logout() {
        val preferences = getSharedPreferences("UsernamePasswordPref", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )
    }

    fun map() {
        startActivity(
            Intent(
                this,
                MapsActivity::class.java
            )
        )
    }

    private fun setting() {
        startActivity(
            Intent(
                this,
                SettingActivity::class.java
            )
        )
    }

    fun Proximitysensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor())
            return
        else {
            Psensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
            flag = false
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
            flag = false
        } else {
            flag
        }
        return flag
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, Psensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)

    }

    var currentX: Float = 0.0f
    var currentY: Float = 0.0f
    var currentZ: Float = 0.0f
    override fun onSensorChanged(event: SensorEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                val values = event.values[0]
                if (values <= 4) {
                    logout()
                }
            }
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                currentX = event.values[0]
                currentY = event.values[1]
                currentZ = event.values[2]
                val acc =
                    (currentX * currentX + currentY * currentY + currentZ * currentZ) / (9.8 * 9.8)
                if (acc >= 35) {
                    vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                300,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                        finish();
                        startActivity(getIntent());
                    } else {
                        vibrator.vibrate(300)
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


}


