package com.ma7moud27.onlinebookshop.model

import com.google.gson.annotations.SerializedName

data class SearchBookResponse(
    @SerializedName("works", alternate = ["docs"])
    val items: List<SearchBookItem>? = null,
    val numFound: Int? = null,
    val start: Int? = null,
)
