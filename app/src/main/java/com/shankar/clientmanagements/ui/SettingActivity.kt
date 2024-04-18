package com.shankar.clientmanagements.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shankar.clientmanagements.R

class SettingActivity : AppCompatActivity() {
    private val setting = arrayOf("Edit Details", "Change Password", "FeedBack")
    private lateinit var SettlingListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        SettlingListView = findViewById(R.id.SettlingListView)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            setting
        )
        SettlingListView.adapter = adapter

        SettlingListView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    startActivity(
                        Intent(this,
                        UpdateActivity::class.java)
                    )
                }
                1 -> {
                    startActivity(
                        Intent(this,
                           ChangePasswordActivity::class.java)
                    )
                }
                else -> {
                    Toast.makeText(this, "this is item third ", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}