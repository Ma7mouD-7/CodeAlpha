package com.ma7moud27.shelfy.ui.booksdisplay.repository

import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.utils.enums.Category

interface BooksDisplayRepository {
    suspend fun getTrending(
        trendTime: String,
        page: Int,
        limit: Int,
    ): SearchBookResponse
    suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int
    ): SearchBookResponse
    fun getCategoryList(numOfItems: Int): List<Category>
    suspend fun getWork(workID: String): Work
}