package com.ma7moud27.shelfy.model

data class SearchAuthorResponse(
    val docs: List<SearchAuthorItem>? = null,
    val numFound: Int? = null,
    val start: Int? = null,
)
