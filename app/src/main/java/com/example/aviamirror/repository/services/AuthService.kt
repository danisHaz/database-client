package com.example.aviamirror.repository.services

import com.example.aviamirror.repository.responses.GenericResponse
import com.example.aviamirror.repository.responses.UserData
import com.example.aviamirror.utils.auth.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("/register/{username}")
    fun registerUser(
        @Path("username") username: String,
        @Body user: User
    ): Call<GenericResponse<Any?>>

    @POST("/login")
    fun loginUser(
        @Body user: User,
    ): Call<GenericResponse<UserData>>

    @POST("/logout/{username}")
    fun logoutUser(
        @Path("username") username: String
    ): Call<GenericResponse<Any?>>
}