package com.ma7moud27.exams.model

sealed class CustomCallback<T> (val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : CustomCallback<T>(data)
    class Loading<T>(data: T? = null) : CustomCallback<T>(data)
    class Error<T>(data: T? = null, message: String) : CustomCallback<T>(data)
}
