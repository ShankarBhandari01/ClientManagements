package com.shankar.mywatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Dashboard : AppCompatActivity() {
    private var progr = 0
    lateinit var text_view_progress: TextView
    lateinit var logout: ImageButton
    private lateinit var progress_Bar: ProgressBar
    private lateinit var tv_remaining: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        progress_Bar = findViewById(R.id.progress_bar)
        text_view_progress = findViewById(R.id.text_view_progress)
        tv_remaining = findViewById(R.id.tv_remaining)
        logout= findViewById(R.id.logout)
        updateProgessbar()
        logout.setOnClickListener {
            logout()
        }
    }


    private fun updateProgessbar() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = ClientsRepository()
            val response = repository.getDays()
            if (response.success == true) {
                progr = response.message!!.toInt()
                withContext(Dispatchers.Main) {
                    progress_Bar.max = 30
                    if (progr > 0) {
                        progress_Bar.progress = progr
                        text_view_progress.text = "${progr} days"
                        tv_remaining.text ="Remaining"
                    } else {
                        progress_Bar.progress = 0
                        text_view_progress.text = "${progr} days"
                        tv_remaining.text ="Expired"

                    }

                }
            }
        }

    }
    fun logout() {
        val preferences = getSharedPreferences("UsernamePasswordPref", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        )
    }
}