package com.example.aviamirror.repository

import android.util.Log
import com.example.aviamirror.repository.responses.GenericResponse
import com.example.aviamirror.repository.responses.UserData
import com.example.aviamirror.repository.services.AuthService
import com.example.aviamirror.utils.Constants
import com.example.aviamirror.utils.Result
import com.example.aviamirror.utils.auth.User
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val authService: AuthService = retrofit.create(AuthService::class.java)

    fun signInAccount(user: User): Flow<Result<User>> = flow {
        emit(Result(isLoading = true))
        val response: GenericResponse<UserData>
        Log.e("repo", "${user.username}, ${user.password}")
        try {
            response = authService.loginUser(user).await()
        } catch (e: java.lang.Exception) {
            emit(Result(isError = true, exception = e))
            return@flow
        }

        response.errorCode?.let {
            emit(Result(isError = true, exception = Exception(response.responseMessage)))
        } ?: emit(Result(data = response.data?.toUser(), isLoading = false))
    }
}