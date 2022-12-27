package com.example.aviamirror.presentation.auth.authscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviamirror.repository.AuthRepository
import com.example.aviamirror.utils.Result
import com.example.aviamirror.utils.auth.AuthSession
import com.example.aviamirror.utils.auth.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthViewModel : ViewModel() {

    private var signInJob: Job? = null
    private val authRepository = AuthRepository()

    val result: MutableLiveData<Result<User>> by lazy {
        MutableLiveData(Result())
    }

    fun signInAccount(user: User) {
        authRepository.signInAccount(user).onEach { res ->
            when {
                res.isLoading -> {
                    result.value = result.value?.copy(isLoading = true, isError = false)
                }
                res.isError -> {
                    result.value = result.value?.copy(isLoading = false, isError = true)
                }
                else -> {
                    res.data?.let {
                        result.value = res
                        AuthSession.user = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}