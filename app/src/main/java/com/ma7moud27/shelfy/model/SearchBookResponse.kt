package com.ma7moud27.shelfy.model

import com.google.gson.annotations.SerializedName

data class SearchBookResponse(
    @SerializedName("works", alternate = ["docs"])
    var items: MutableList<SearchBookItem>? = null,
    val numFound: Int? = null,
    val start: Int? = null,
)
