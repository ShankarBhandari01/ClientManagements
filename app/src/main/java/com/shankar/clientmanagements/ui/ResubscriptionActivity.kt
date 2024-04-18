package com.shankar.clientmanagements.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResubscriptionActivity : AppCompatActivity() {
    private lateinit var subscription: EditText
    private lateinit var Date: EditText
    private lateinit var Submit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resubscription2)
        subscription = findViewById(R.id.subscription)
        Date = findViewById(R.id.Date)
        Submit = findViewById(R.id.Submit)
        CoroutineScope(Dispatchers.IO).launch {
            val user = RoomDataBase.getInstance(this@ResubscriptionActivity).getUserResponseDAO()
                .getClients()
            withContext(Main) {
                subscription.setText(user[0].subscriptionType).toString()
                Date.setText(user[0].subscriptionTO!!.split('T')[0])
            }
        }
        Submit.setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        try {
            CoroutineScope(Dispatchers.IO).launch {

                val subscription = subscription.text.toString()
                val date = Date.text.toString()
                val repository = ClientsRepository()
                val response = repository.UpdateResub(date, subscription)
                if (response.success == true) {
                    withContext(Main) {
                        Toast.makeText(
                            this@ResubscriptionActivity,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Main) {
                        Toast.makeText(
                            this@ResubscriptionActivity,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (ex: Exception) {
            Toast.makeText(this, ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }
}