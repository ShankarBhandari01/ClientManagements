package com.shankar.clientmanagements.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.adapter.RequestAdapter
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = View.findViewById(R.id.RecyclerView)
        textView = View.findViewById(R.id.textView)
        loadDetails()
        return View
    }

    private fun loadDetails() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = ClientsRepository()
                val response = repository.getRequest()
                if (response.success == true) {
                    val alllst = response.allReq
                    if (alllst!!.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            textView.text = "You Dont Have Notification"
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            recyclerView.adapter =
                                context?.let { RequestAdapter(it, alllst) }
                            recyclerView.layoutManager =
                                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                        }
                    }

                }
            }
        } catch (Ex: Exception) {
            Toast.makeText(context, Ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }
}