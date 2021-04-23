package com.madan.bookhotel.response

import com.madan.bookhotel.entity.User

data class LoginResponse (
        val success : Boolean? = null,
        val token : String? = null,
        val data : User? = null
)