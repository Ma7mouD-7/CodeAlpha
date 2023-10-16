package com.ma7moud27.onlinebookshop.ui.booksdisplay.repository

import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.utils.enums.Category

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