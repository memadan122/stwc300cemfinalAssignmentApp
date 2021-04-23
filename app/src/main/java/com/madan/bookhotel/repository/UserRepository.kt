package com.madan.bookhotel.repository

import com.madan.bookhotel.api.MyApi
import com.madan.bookhotel.api.MyApiRequest
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.entity.User
import com.madan.bookhotel.response.ImageResponse
import com.madan.bookhotel.response.LoginResponse
import okhttp3.MultipartBody

class UserRepository: MyApiRequest() {
    val myApi = ServiceBuilder.buildService(MyApi::class.java)

    suspend fun registerUser(user: User): LoginResponse{
        return apiRequest {
            myApi.registerUser(user)
        }
    }

    suspend fun checkUser(username : String, password: String): LoginResponse{
        return apiRequest {
            myApi.checkUser(username, password)
        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            myApi.uploadImage( id, body)
        }
    }
}