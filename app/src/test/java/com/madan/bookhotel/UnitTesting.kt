package com.madan.bookhotel

import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.entity.User
import com.madan.bookhotel.repository.BookingRepository
import com.madan.bookhotel.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UnitTesting {

    @Test
    fun registerUser() = runBlocking {
        val user = User(firstname = "Madan", lastname = "Madan", address = "Ktm", email = "madan5@gmail.com", phone = "98765432", username = "madan2", password = "madan123" )
        val userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun checkUser() = runBlocking {
        val userRepository = UserRepository()
        val response = userRepository.checkUser("madan1", "madan123")
        ServiceBuilder.token = response.token
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun registerUserfailed() = runBlocking {
        val user =
                User(firstname = "", lastname = "Bastakoti", address = "gorkha", email = "madan@gmail.com", username = "madan", password = "madan")
        val userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun checkUserFailed() = runBlocking {
        val userRepository = UserRepository()
        val response = userRepository.checkUser("madan", "madan13")
        ServiceBuilder.token = response.token
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun GetBookings() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("madan","madan123").token
        val booking = BookingRepository()
        val response = booking.getBooking()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun GetBookingsfailed() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("madan","madan111").token
        val booking = BookingRepository()
        val response = booking.getBooking()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun DeleteBookings() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("madan","madan123").token
        val repository = BookingRepository()
        val response = repository.deleteBooking(id = "60827374956512024c98ffdd")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun DeleteBookingsFailed() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("madan","11madan12311").token
        val repository = BookingRepository()
        val response = repository.deleteBooking(id = "607b0e4ceedadb07ec2a")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

}