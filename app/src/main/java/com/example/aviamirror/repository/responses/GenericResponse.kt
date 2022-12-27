package com.example.aviamirror.repository.responses

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("error")
    val errorCode: Int?,
    @SerializedName("response")
    val responseMessage: String?,
    @SerializedName("data")
    val data: T?,
)