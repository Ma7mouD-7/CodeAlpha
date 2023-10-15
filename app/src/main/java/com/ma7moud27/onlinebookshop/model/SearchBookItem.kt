package com.ma7moud27.onlinebookshop.model

import com.google.gson.annotations.SerializedName

data class SearchBookItem(
    val key: String? = null,
    val title: String? = null,
    @SerializedName("author_name")
    val authorName: List<String>? = null,
    @SerializedName("cover_i")
    val coverID: Int? = null,
    @SerializedName("cover_edition_key")
    val coverEditionKey: String? = null,
    @SerializedName("first_publish_year")
    val publishYear: Int? = null,
    @SerializedName("number_of_pages_median")
    val pagesNumber: Int? = null,
    @SerializedName("lending_edition_s")
    val lendingEditionKey: String? = null,
)
