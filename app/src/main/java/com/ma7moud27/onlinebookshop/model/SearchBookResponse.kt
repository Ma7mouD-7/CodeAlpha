package com.ma7moud27.onlinebookshop.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SearchBookResponse(
//    @JsonAlias("docs", "works")
    @SerializedName("works", alternate = ["docs"])
//    @SerializedName("docs")
    val items: List<SearchBookItem> = listOf(),
    val numFound: Int = 0,
    val start: Int = 0
)