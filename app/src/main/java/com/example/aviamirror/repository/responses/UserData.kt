package com.example.aviamirror.repository.responses

import com.example.aviamirror.utils.auth.User
import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("clientId")
    val clientId: Int,
    @SerializedName("phone_number")
    val phone_number: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("signedIn")
    val isSignedIn: Boolean
) {
    fun toUser() = User(email, "stub", clientId)
}