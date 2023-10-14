package com.ma7moud27.onlinebookshop.model

import com.google.gson.annotations.SerializedName
import com.ma7moud27.onlinebookshop.utils.Constants

data class SearchBookItem(
    val key: String = "",
    val title: String = "",
    @SerializedName("author_name")
    val authorName: List<String> = listOf(),
    @SerializedName("cover_i")
    val coverID: Int = 0,
    @SerializedName("cover_edition_key")
    val coverEditionKey: String = "",
    @SerializedName("first_publish_year")
    val publishYear: Int = 0,
    @SerializedName("number_of_pages_median")
    val pagesNumber: Int = 0,
    @SerializedName("lending_edition_s")
    val lendingEditionKey: String = "",
)