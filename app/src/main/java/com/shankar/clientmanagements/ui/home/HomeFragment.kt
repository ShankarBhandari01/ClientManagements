package com.shankar.clientmanagements.ui.home

import android.app.PendingIntent
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.adapter.ShowAdapter
import com.shankar.clientmanagements.db.RoomDataBase
import com.shankar.clientmanagements.notification.NotificationChannel
import com.shankar.clientmanagements.repository.ClientsRepository
import com.shankar.clientmanagements.repository.OfferRepository
import com.shankar.clientmanagements.ui.Dashboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment(), SensorEventListener {

    private lateinit var recyclerView: RecyclerView
    private var progr = 0
    private lateinit var progress_Bar: ProgressBar
    private lateinit var tv_remaining: TextView
    private lateinit var text_view_progress: TextView
    private lateinit var subsriptionName: TextView
    private lateinit var subsriptionDate: TextView
    private lateinit var subsriptionEnd: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = View.findViewById(R.id.featureRecyclerView)
        progress_Bar = View.findViewById(R.id.progress_bar)
        text_view_progress = View.findViewById(R.id.text_view_progress)
        tv_remaining = View.findViewById(R.id.tv_remaining)
        subsriptionName = View.findViewById(R.id.subsriptionName)
        subsriptionEnd = View.findViewById(R.id.subsriptionEnd)
        subsriptionDate = View.findViewById(R.id.subsriptionDate)

        loadDetails()
        updateProgessbar()
        subscription()


        return View
    }

    private fun loadDetails() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                context?.let { RoomDataBase.getInstance(it).GetOfferResponseDAO().deleteTables() }
                val repository = OfferRepository()
                val response = repository.getOffers()

                if (response.success == true) {
                    context?.let {
                        RoomDataBase.getInstance(it).GetOfferResponseDAO()
                            .insertData(response.allpackage!!)
                    }

                    val alllst =
                        context?.let {
                            RoomDataBase.getInstance(it).GetOfferResponseDAO().getAllOffers()
                        }
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter =
                            context?.let { ShowAdapter(it, alllst!!) }
                        recyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    }
                }

            }
        } catch (Ex: Exception) {
            Toast.makeText(context, Ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }


    private fun updateProgessbar() {
        try {
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
                        } else {
                            progress_Bar.progress = 0
                            text_view_progress.text = "${progr} days"
                            tv_remaining.text = "Subscriptions is expired!!"
                            if (progr < 0) {
                                val stateIntent = Intent(context, Dashboard::class.java)
                                stateIntent.putExtra("id", 100)
                                val pendingIntent = PendingIntent.getBroadcast(
                                    context,
                                    0,
                                    stateIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                                )
                                val notificationManager =
                                    context?.let { NotificationManagerCompat.from(it) }

                                val notificationChannels = context?.let { NotificationChannel(it) }
                                notificationChannels!!.notificationChannels()

                                val notification =
                                    context?.let {
                                        NotificationCompat.Builder(
                                            it,
                                            notificationChannels.Channel_1
                                        )
                                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                            .setContentTitle("Subscription")
                                            .setContentText("Your Subscriptions is expired !!")
                                            .setColor(Color.BLUE)
                                            .setAutoCancel(true).setContentIntent(pendingIntent)
                                            .build()

                                    }

                                notificationManager!!.notify(1, notification!!)
                            }


                        }

                    }
                } else {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (Ex: Exception) {
            Toast.makeText(context, Ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }

    private fun subscription() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val user =
                    context?.let { RoomDataBase.getInstance(it).getUserResponseDAO().getClients() }
                withContext(Dispatchers.Main) {
                    subsriptionName.text = user!![0].subscriptionType.toString()
                    subsriptionDate.text = user!![0].subscriptionDate.toString()
                    subsriptionEnd.text = user!![0].subscriptionTO.toString().split('T')[0]

                }


            }
        } catch (ex: Exception) {
            Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }


}