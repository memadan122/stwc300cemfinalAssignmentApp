package com.madan.bookhotel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Destination(
        @PrimaryKey
    val _id : String= "",
        val dimage : String? = null,
        val dname : String? = null,
        val ddetails : String? = null,
        var dprice : String? = null
)


//@Entity
//data class User(
//    var etFirstName: String? = null,
//    var etLastName: String? = null,
//    var etAddress: String? = null,
//    var etEmailAddress: String? = null,
//    var etPhoneNumber: String? = null,
//    var etUsername: String? = null,
//    var etPassword: String? = null,
//    var etConfirmPassword: String? = null
//    ){
//    @PrimaryKey(autoGenerate = true)
//    var UserId: Int = 0
//}