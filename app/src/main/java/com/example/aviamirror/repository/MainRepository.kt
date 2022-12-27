package com.example.aviamirror.repository

import android.util.Log
import com.example.aviamirror.repository.responses.BuyTicketResponse
import com.example.aviamirror.repository.responses.Order
import com.example.aviamirror.repository.responses.TakeoffResponse
import com.example.aviamirror.repository.services.MainService
import com.example.aviamirror.utils.Constants
import com.example.aviamirror.utils.Result
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val mainService = retrofit.create(MainService::class.java)

    fun getPopularTakeoffs(): Flow<Result<List<TakeoffResponse>>> = flow {
        emit(Result(isLoading=true))

        val takeoffsResponse: List<TakeoffResponse>
        try {
            takeoffsResponse = mainService.getPopularTakeoffs().await()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result(isError=true, exception=e))
            return@flow
        }

        emit(Result(data = takeoffsResponse))
    }

    fun getCitiesList(): Flow<Result<List<String>>> = flow {
        emit(Result(isLoading=true))

        val cities: List<String>
        try {
            cities = mainService.getCitiesList().await()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result(isError=true, exception=e))
            return@flow
        }

        emit(Result(data = cities))
    }

    fun findByFilterQuery(filter: TakeoffResponse): Flow<Result<List<TakeoffResponse>>> = flow {
        emit(Result(isLoading=true))

        val takeoffsResponse: List<TakeoffResponse>
        try {
            takeoffsResponse = mainService.findByFilterQuery(filter).await()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result(isError=true, exception=e))
            return@flow
        }

        emit(Result(data = takeoffsResponse))
    }

    fun getTicketData(timetableId: Int): Flow<Result<BuyTicketResponse>> = flow {
        emit(Result(isLoading = true))

        val buyTicketResponse: BuyTicketResponse
        try {
            buyTicketResponse = mainService.getTicketData(timetableId).await()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result(isError = true, exception = e))
            return@flow
        }

        emit(Result(data = buyTicketResponse))
    }

    fun addOrderToHistory(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            val stub = mainService.addTicketToOrder(order).await()
            Log.e("MainRepo", stub.data.toString())
        }
    }
}