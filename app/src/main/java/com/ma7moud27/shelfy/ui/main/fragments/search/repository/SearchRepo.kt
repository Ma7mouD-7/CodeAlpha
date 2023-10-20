package com.ma7moud27.shelfy.ui.main.fragments.search.repository

import com.ma7moud27.shelfy.model.SearchAuthorResponse
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.work.Work

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

    suspend fun getWork(workID: String): Work
}
