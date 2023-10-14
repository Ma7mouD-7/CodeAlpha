package com.ma7moud27.onlinebookshop.model

import com.google.gson.annotations.SerializedName

data class SearchBookResponse(
//    @JsonAlias("docs", "works")
    //    @SerializedName("docs")
    @SerializedName("works", alternate = ["docs"])
    val items: List<SearchBookItem> = listOf(),
    val numFound: Int = 0,
    val start: Int = 0,
)
