package com.ma7moud27.onlinebookshop.repository.search

import com.ma7moud27.onlinebookshop.model.SearchAuthorResponse
import com.ma7moud27.onlinebookshop.model.SearchBookResponse

interface SearchRepo {
    suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int
    ): SearchBookResponse

    suspend fun searchAuthors(
        query: String,
        page: Int,
        sort: String,
    ): SearchAuthorResponse
}
