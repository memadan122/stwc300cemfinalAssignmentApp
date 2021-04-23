package com.madan.bookhotel.repository

import com.madan.bookhotel.api.MyApiRequest
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotelwear.Entity.Destination
import com.madan.bookhotelwear.response.DestinationResponse
import com.madan.bookhotelwear.response.ImageResponse

import okhttp3.MultipartBody

class DestinationRepository: MyApiRequest() {
    val DestinationApi = ServiceBuilder.buildService(com.madan.bookhotel.api.DestinationApi::class.java)

    suspend fun insertDestination(destination: Destination): DestinationResponse {
        return apiRequest {
            DestinationApi.Insert_Destination(ServiceBuilder.token!!, destination)
        }
    }

    suspend fun getDestination(): DestinationResponse {
        return apiRequest {
            DestinationApi.getDestination(ServiceBuilder.token!!)

        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            DestinationApi.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }

    suspend fun addToCart(destination: Destination):ImageResponse{
        return apiRequest {
            DestinationApi.book(ServiceBuilder.token!!,destination)
        }
    }
}


