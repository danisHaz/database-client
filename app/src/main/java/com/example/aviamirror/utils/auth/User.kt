package com.example.aviamirror.utils.auth

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("clientId")
    val clientId: Int = 0,
) {
    val isDataValid: Boolean
        get() = isPasswordValid(password) && isUserNameValid(username)


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else if (username.length == 11 || username.length == 12) {
            Patterns.PHONE.matcher(username).matches()
        } else {
            false
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}