package com.ma7moud27.shelfy.model

import com.google.gson.annotations.SerializedName

data class BookShelf(
    val counts: Counts? = Counts(),
)

data class Counts(
    @SerializedName("want_to_read") val countWantToRead: Int? = null,
    @SerializedName("currently_reading") val countCurrentlyReading: Int? = null,
    @SerializedName("already_read") val countAlreadyRead: Int? = null,
)
