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
        val user = User(firstname = "Avi", lastname = "kc", address = "bkt", email = "abc@gmail.com", phone = "986353517", username = "Avi", password = "12345" )
        val userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun checkUser() = runBlocking {
        val userRepository = UserRepository()
        val response = userRepository.checkUser("Aakash", "1111")
        ServiceBuilder.token = response.token
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun registerUserfailed() = runBlocking {
        val user =
                User(firstname = "", lastname = "khatri", address = "Duwakot", email = "aakash@gmail.com", username = "Aakash", password = "khatri")
        val userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun checkUserFailed() = runBlocking {
        val userRepository = UserRepository()
        val response = userRepository.checkUser("Aakashhh", "khatri")
        ServiceBuilder.token = response.token
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun GetBookings() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("Aakash","1111").token
        val booking = BookingRepository()
        val response = booking.getBooking()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun GetBookingsfailed() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("Aakashhhhh","11111111").token
        val booking = BookingRepository()
        val response = booking.getBooking()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun DeleteBookings() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("Aakash","1111").token
        val repository = BookingRepository()
        val response = repository.deleteBooking(id = "607b0e4ceedadb07ec2a8f29")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun DeleteBookingsFailed() = runBlocking {
        ServiceBuilder.token="Bearer "+UserRepository().checkUser("Aakash","1111").token
        val repository = BookingRepository()
        val response = repository.deleteBooking(id = "607b0e4ceedadb07ec2a")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

}