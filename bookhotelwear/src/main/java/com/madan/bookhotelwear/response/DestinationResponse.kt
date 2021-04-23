package com.madan.bookhotelwear.response

import com.madan.bookhotelwear.Entity.Destination


data class DestinationResponse (
    val success : Boolean? = null,
    val data : MutableList<Destination>? = null
    )