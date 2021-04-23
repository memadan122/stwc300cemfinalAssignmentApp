package com.madan.bookhotel.response

import com.madan.bookhotel.entity.Booking

data class BookingUpdateResponse (
    val success: Boolean? =null,
    val data : Booking? = null
    )