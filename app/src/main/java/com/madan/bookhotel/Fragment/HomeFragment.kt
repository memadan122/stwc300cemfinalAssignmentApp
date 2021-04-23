package com.madan.bookhotel.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madan.bookhotel.R
import com.madan.bookhotel.adapter.BookingAdapter
import com.madan.bookhotel.entity.Booking
import com.madan.bookhotel.repository.BookingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookings : MutableList<Booking>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val booking = BookingRepository()
                val response = booking.getBooking()
                if (response.success == true){
//                    put all the bookings details in lstBooking
                    bookings = response.data!!


                    withContext(Dispatchers.Main){
                        recyclerView = view.findViewById(R.id.bookingRecyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                        if (container != null) {
                            recyclerView.adapter = BookingAdapter(container.context, bookings)
                        }
                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

}

