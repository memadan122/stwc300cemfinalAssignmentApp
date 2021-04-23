package com.madan.bookhotel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey
    val _id : String = "",
    val firstname : String? = null,
    val lastname : String? = null,
    val address : String? =null,
    val email : String? = null,
    val phone : String? = null,
    val username : String? = null,
    val password : String? = null,
)
