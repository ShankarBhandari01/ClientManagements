package com.shankar.clientmanagements.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.entity.password
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var btnChange: Button
    private lateinit var OldPassword: TextInputEditText
    private lateinit var NewPassword: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        binding()
    }

    private fun binding() {
        NewPassword = findViewById(R.id.NewPassword)
        OldPassword = findViewById(R.id.OldPassword)
        btnChange = findViewById(R.id.btnChange)
        btnChange.setOnClickListener {
            val password = password(
                password = OldPassword.text.toString(),
                NewPassword = NewPassword.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val datas =
                    RoomDataBase.getInstance(this@ChangePasswordActivity).getUserResponseDAO()
                        .getClients()
                try {
                    val repository = ClientsRepository()
                    val response = repository.updatePassword(password)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ChangePasswordActivity,
                                "${response.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ChangePasswordActivity,
                                "${response.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }

    }
}