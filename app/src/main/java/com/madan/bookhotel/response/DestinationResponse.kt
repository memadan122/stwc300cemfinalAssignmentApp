package com.madan.bookhotel.response

import com.madan.bookhotel.entity.Destination

data class DestinationResponse (
    val success : Boolean? = null,
    val data : MutableList<Destination>? = null
    )