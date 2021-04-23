package com.madan.bookhotel.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.madan.bookhotel.R
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.entity.Booking
import com.madan.bookhotel.repository.BookingRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookingAdapter (
        private val context : Context,
        private val lstBookings : MutableList<Booking>
        ) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

            class BookingViewHolder(view: View): RecyclerView.ViewHolder(view){

                var cvImage : ImageView
                var tvName : TextView
                var tvPerson : TextView
                var tvPrice : TextView
                var tvDelete : ImageButton
                var tvMinus : TextView
                var tvAdd : TextView

                init {
                    cvImage =view.findViewById(R.id.cvImage)
                    tvName = view.findViewById(R.id.tvName)
                    tvPerson = view.findViewById(R.id.tvPerson)
                    tvPrice = view.findViewById(R.id.tvPrice)
                    tvDelete = view.findViewById(R.id.cancel_button)
                    tvMinus = view.findViewById(R.id.tvMinus)
                    tvAdd = view.findViewById(R.id.tvAdd)

                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bookingpage_layout, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = lstBookings[position]

        if (booking.UserId == ServiceBuilder.user?._id) {
            Glide.with(context).load("${ServiceBuilder.BASE_URL}${booking.Destination!!.dimage}").into(holder.cvImage)
            holder.tvName.text = booking.Destination!!.dname
            holder.tvPerson.text = booking.Person.toString()
            holder.tvPrice.text = "Rs " + booking.Destination.dprice
        } else {
            holder.tvName.text = "No Relevent data"
        }


        holder.tvDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            //set title for alert dialog
            builder.setTitle("Confirm Delete")
            builder.setMessage("Are you Sure you want to Cancel?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = BookingRepository()
                        val response = repository.deleteBooking(id = booking._id)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        context,
                                        "You have deleted Application.", Toast.LENGTH_SHORT
                                ).show()
                                lstBookings.remove(booking)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    ex.toString(), Toast.LENGTH_SHORT
                            ).show()
                            ex.printStackTrace()
                        }
                    }
                }
            }
            //performing cancel action
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
                Toast.makeText(context, "clicked cancel", Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }

        holder.tvAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = BookingRepository()
                    val response = repository.updateBooking(id = booking._id, Person = booking.Person!! + 1)
                    println("hello")
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    "Updated", Toast.LENGTH_SHORT
                            ).show()
                            holder.tvPerson.text = response.data!!.Person.toString()
                            holder.tvPrice.text = (booking.Destination!!.dprice?.let { it1 -> response.data!!.Person?.toInt()?.times(it1.toInt()) }).toString()
                            notifyDataSetChanged()

                        }
                    }
                } catch (err: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                context,
                                err.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }

                holder.tvMinus.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repository = BookingRepository()
                            val response = repository.updateBooking(id = booking._id, Person = booking.Person!! - 1)
                            println("hello")
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                            context,
                                            "Updated", Toast.LENGTH_SHORT
                                    ).show()
                                    holder.tvPerson.text = response.data!!.Person.toString()
                                    holder.tvPrice.text = (booking.Destination!!.dprice?.let { it1 -> response.data!!.Person?.toInt()?.times(it1.toInt()) }).toString()
                                    notifyDataSetChanged()

                                }
                            }
                        } catch (err: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        context,
                                        err.toString(), Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                    }


                }
            }
        }


        override fun getItemCount(): Int {
            return lstBookings.size
        }

}