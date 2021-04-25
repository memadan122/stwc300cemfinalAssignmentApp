package com.madan.bookhotel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.repository.DestinationRepository


import com.bumptech.glide.Glide
import com.madan.bookhotelwear.Entity.Destination
import com.madan.bookhotelwear.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DestinationAdpater (
    private val context : Context,
    private val lstDestinations : MutableList<Destination>
): RecyclerView.Adapter<DestinationAdpater.DestinationViewHolder>(){

    class DestinationViewHolder(view: View): RecyclerView.ViewHolder(view){
        var dimage : ImageView
        var dname : TextView
        var ddetails : TextView
        var dprice : TextView
        var btnBook: TextView
        init {
            dimage =view.findViewById(R.id.dimage)
            dname = view.findViewById(R.id.tvName)
            ddetails = view.findViewById(R.id.tvdetails)
            dprice = view.findViewById(R.id.tvPrice)
            btnBook = view.findViewById(R.id.tvBook)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.dash_layout,parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = lstDestinations[position]

        holder.dname.text = destination.dname
        holder.ddetails.text = destination.ddetails
        holder.dprice.text = destination.dprice


        var imgPath = ServiceBuilder.loadImg()+destination.dimage!!.replace("\\","/")
        Glide.with(context).load(imgPath).into(holder.dimage)

        holder.btnBook.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {


                val repository = DestinationRepository()
                val response = repository.addToCart(destination)
                if (response.success == true) {
                    withContext(Main) {
                        Toast.makeText(context, "One Destination Booked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return lstDestinations.size
    }
}