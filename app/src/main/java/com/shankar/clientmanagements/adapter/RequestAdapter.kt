package com.shankar.clientmanagements.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.entity.resubs
import com.shankar.clientmanagements.repository.ClientsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RequestAdapter(
    private val context: Context,
    private val lstFeature: MutableList<resubs>
) : RecyclerView.Adapter<RequestAdapter.ShowViewHolder>() {
    class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Type: TextView = view.findViewById(R.id.tvType)
        val Date_To: TextView = view.findViewById(R.id.tvDate)
        val tvRequested: TextView = view.findViewById(R.id.tvRequested)
        val btndel: ImageView = view.findViewById(R.id.btndel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_layout_request, parent, false)

        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = lstFeature[position]
        holder.Type.text = show.subscriptionType
        holder.Date_To.text = show.subscriptionTO
        holder.tvRequested.text = show.CreateDate!!.split("GMT")[0]

        holder.btndel.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Request")
            builder.setMessage("Are you sure you want to delete ${show.subscriptionType} ??")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { _, _ ->
                deleteRequest(show._id!!)
            }
            builder.setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "Action cancelled", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }


    }

    private fun deleteRequest(id: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = ClientsRepository()
                val response = repository.deleteRequest(id)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        } catch (ex: Exception) {
            Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {
        return lstFeature.size
    }


}