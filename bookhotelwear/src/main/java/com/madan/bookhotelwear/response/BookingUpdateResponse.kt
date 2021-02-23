package com.madan.bookhotelwear.response

import com.madan.bookhotel.entity.Booking

data class BookingUpdateResponse (
    val success: Boolean? =null,
    val data : Booking? = null
    )