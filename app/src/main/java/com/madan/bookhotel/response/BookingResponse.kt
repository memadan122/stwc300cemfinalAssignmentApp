package com.madan.bookhotel.response

import com.madan.bookhotel.entity.Booking

data class BookingResponse (
    val success: Boolean? =null,
    val data : MutableList<Booking>? = null
    )