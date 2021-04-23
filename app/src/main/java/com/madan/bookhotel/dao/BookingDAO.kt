package com.madan.bookhotel.dao

import androidx.room.Query
import com.madan.bookhotel.entity.Booking

interface BookingDAO {
    @Query("select* from booking")
    suspend fun  getBooking() : MutableList<Booking>
}