package com.ma7moud27.onlinebookshop.ui.main.fragments.search.repository

import com.ma7moud27.onlinebookshop.model.SearchAuthorResponse
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.work.Work

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
