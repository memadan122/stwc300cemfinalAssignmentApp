package com.madan.bookhotel.api

import com.madan.bookhotelwear.Entity.User
import com.madan.bookhotelwear.response.ImageResponse
import com.madan.bookhotelwear.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MyApi {
    //Register User
    @POST("/register")
    suspend fun registerUser(@Body user: User): Response<LoginResponse>

    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
            @Field("username") username : String,
            @Field("password") password :String,
    ): Response<LoginResponse>

    @Multipart
    @PUT("/photo/{id}")
    suspend fun uploadImage(
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

}