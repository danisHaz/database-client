package com.example.aviamirror.utils

import java.lang.Exception

data class Result<out T: Any?>(
    val data: T? = null,
    val isError: Boolean = false,
    val exception: Exception? = null,
    val isLoading: Boolean = false
)