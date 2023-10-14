package com.ma7moud27.onlinebookshop.model

import com.google.gson.annotations.SerializedName

data class BookShelf(
    val counts: Counts = Counts()
)

data class Counts(
    @SerializedName("want_to_read")
    val countWantToRead: Int = 0,
    @SerializedName("currently_reading")
    val countCurrentlyReading: Int = 0,
    @SerializedName("already_read")
    val countAlreadyRead: Int = 0
)