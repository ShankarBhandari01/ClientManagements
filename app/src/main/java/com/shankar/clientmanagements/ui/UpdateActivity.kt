package com.shankar.clientmanagements.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.entity.Clients
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateActivity : AppCompatActivity() {
    private lateinit var FullName: EditText
    private lateinit var TUpdate: Button
    private lateinit var Dob: EditText
    private lateinit var citizenshipNumber: EditText
    private lateinit var Address: EditText
    private lateinit var Phone: EditText
    private lateinit var gender: EditText
    private lateinit var UserName: EditText
    var data: MutableList<Clients>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        binding()
        update()


    }

    private fun binding() {
        FullName = findViewById(R.id.FullName)
        TUpdate = findViewById(R.id.TUpdate)
        Dob = findViewById(R.id.Dob)
        citizenshipNumber = findViewById(R.id.citizenshipNumber)
        Address = findViewById(R.id.Address)
        Phone = findViewById(R.id.Phone)
        gender = findViewById(R.id.gender)
        UserName = findViewById(R.id.UserName)

        CoroutineScope(Dispatchers.IO).launch {
            data =
                RoomDataBase.getInstance(this@UpdateActivity).getUserResponseDAO().getClients()
            withContext(Main) {
                FullName.setText(data!![0].full_name)
                citizenshipNumber.setText((data!![0].citizenshipNumber))
                Address.setText(data!![0].address)
                Phone.setText(data!![0].contact)
                gender.setText(data!![0].gender)
                UserName.setText((data!![0].username))
                Dob.setText(data!![0].Dob)

            }
        }


    }

    private fun update() {
        TUpdate.setOnClickListener {
            val clients =
                Clients(
                    full_name = FullName.text.toString(),
                    Dob = Dob.text.toString(),
                    gender = gender.text.toString(),
                    address = Address.text.toString(),
                    contact = Phone.text.toString(),
                    username = data!![0].username.toString(),
                    citizenshipNumber = citizenshipNumber.text.toString(),
                    image = data!![0].image.toString(),
                    subscriptionType = data!![0].subscriptionType.toString(),
                    subscriptionDate = data!![0].subscriptionDate.toString(),
                    subscriptionTO = data!![0].subscriptionTO.toString(),
                    password = data!![0].password.toString(),
                    CreateDate = data!![0].CreateDate.toString(),
                    usertype = data!![0].usertype.toString()

                )
            clients.userId = data!![0].userId

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    RoomDataBase.getInstance(this@UpdateActivity).getUserResponseDAO()
                        .updateData(clients)

                    val datas = RoomDataBase.getInstance(this@UpdateActivity).getUserResponseDAO()
                        .getClients()

                    val repository = ClientsRepository()
                    val response = repository.update(data!![0]._id.toString(), datas[0])
                    if (response.success == true) {
                        withContext(Main) {
                            Toast.makeText(
                                this@UpdateActivity,
                                "${response.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        withContext(Main) {
                            Toast.makeText(
                                this@UpdateActivity,
                                "${response.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Main) {
                        Toast.makeText(
                            this@UpdateActivity,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }
}