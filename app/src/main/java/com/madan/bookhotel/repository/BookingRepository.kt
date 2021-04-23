package com.madan.bookhotel.repository

import com.madan.bookhotel.api.BookingApi
import com.madan.bookhotel.api.MyApiRequest
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.response.BookingResponse
import com.madan.bookhotel.response.BookingUpdateResponse

class BookingRepository: MyApiRequest() {
    val BookingApi = ServiceBuilder.buildService(BookingApi::class.java)

    suspend fun getBooking(): BookingResponse{
        return apiRequest {
            BookingApi.getBooking(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteBooking(id: String): BookingResponse{
        return apiRequest {
            BookingApi.deleteBooking(ServiceBuilder.token!!, id)
        }
    }

    suspend fun updateBooking(id: String, Person: Int) : BookingUpdateResponse{
        return apiRequest {
            BookingApi.updateBooking(Person = Person, id = id, token = ServiceBuilder.token!!)
        }
    }
}