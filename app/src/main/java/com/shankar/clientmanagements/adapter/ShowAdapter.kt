package com.shankar.clientmanagements.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.api.ServiceBuilder
import com.shankar.clientmanagements.entity.allpackage

class ShowAdapter(
    private val context: Context,
    private val lstFeature: MutableList<allpackage>
) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {
    class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvprice: TextView = view.findViewById(R.id.tvprice)
        val picture: ImageView = view.findViewById(R.id.Picture)
        val tv_descriotion: TextView = view.findViewById(R.id.tv_descriotion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.show_custom_layout, parent, false)

        return ShowViewHolder(view)

    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = lstFeature[position]
        holder.tvTitle.text = show.title
        holder.tvprice.text = show.price
        holder.tv_descriotion.text = show.description
        val imgepath = ServiceBuilder.loadImagePath() + show.image!!.split("\\")[0]
        Glide.with(context)
            .load(imgepath)
            .fitCenter()
            .into(holder.picture)


    }

    override fun getItemCount(): Int {
        return lstFeature.size
    }

}
