package com.ma7moud27.shelfy.model

sealed class ServiceResponse<T> (val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ServiceResponse<T>(data)
    class Loading<T>(data: T? = null) : ServiceResponse<T>(data)
    class Error<T>(data: T? = null, message: String) : ServiceResponse<T>(data)
}
