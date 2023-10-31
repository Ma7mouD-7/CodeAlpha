package com.example.collegealert.model

/*
* Created by Ma7mouD on Mon 30/10/2023 at 05:32 AM.
*/
sealed class Data<T> (val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Data<T>(data)
    class Loading<T>(data: T? = null) : Data<T>(data)
    class Error<T>(data: T? = null, message: String) : Data<T>(data, message)
}
