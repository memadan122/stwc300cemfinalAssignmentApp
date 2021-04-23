package com.madan.bookhotel.api

import com.madan.bookhotel.entity.Destination
import com.madan.bookhotel.response.DestinationResponse
import com.madan.bookhotel.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface DestinationApi {
    @POST("destination/insert")
    suspend fun Insert_Destination(
        @Header("Authorization") token: String,
        @Body destination: Destination
    ):Response<DestinationResponse>

    @GET("destination/showall")
    suspend fun getDestination(
        @Header("Authorization") token: String
    ):Response<DestinationResponse>


    @Multipart
    @PUT("student/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    @POST("/destination/book")
    suspend fun book(@Header("authorization")token:String,@Body destination: Destination):Response<ImageResponse>
}