package com.ma7moud27.onlinebookshop.model

data class SearchAuthorResponse(
    val docs: List<SearchAuthorItem> = listOf(),
    val numFound: Int = 0,
    val start: Int = 0
)