package com.madan.bookhotelwear.repository

import com.madan.bookhotel.api.MyApi
import com.madan.bookhotel.api.MyApiRequest
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotelwear.response.LoginResponse


class UserRepository: MyApiRequest(){
    val myApi = ServiceBuilder.buildService(MyApi::class.java)

//    suspend fun registerUser(user: User): LoginResponse {
//        return apiRequest {
//            myApi.registerUser(user)
//        }
//    }

    suspend fun checkUser(username : String, password: String): LoginResponse {
        return apiRequest {
            myApi.checkUser(username, password)
        }
    }
}