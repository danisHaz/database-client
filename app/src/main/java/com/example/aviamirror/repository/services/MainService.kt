package com.example.aviamirror.repository.services

import com.example.aviamirror.repository.responses.BuyTicketResponse
import com.example.aviamirror.repository.responses.GenericResponse
import com.example.aviamirror.repository.responses.Order
import com.example.aviamirror.repository.responses.TakeoffResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MainService {

    @GET("/popularTakeoffs")
    fun getPopularTakeoffs(): Call<List<TakeoffResponse>>

    @GET("/getCities")
    fun getCitiesList(): Call<List<String>>

    @POST("/findByFilterQuery")
    fun findByFilterQuery(
        @Body data: TakeoffResponse
    ): Call<List<TakeoffResponse>>

    @POST("/getTicketData")
    fun getTicketData(
        @Body timetableId: Int,
    ): Call<BuyTicketResponse>

    @POST("/addToOrderHistory")
    fun addTicketToOrder(
        @Body order: Order,
    ): Call<GenericResponse<Int>>
}