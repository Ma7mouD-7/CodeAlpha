package com.ma7moud27.onlinebookshop.ui.main.fragments.home.repository

import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.utils.enums.Category
import okhttp3.ResponseBody

interface HomeRepository {
    fun getCategoryList(numOfItems:Int) : List<Category>
    suspend fun getTrending(
        trendTime: String,
        page: Int,
        limit: Int
    ): SearchBookResponse
    suspend fun getWork(workID:String): Work
    suspend fun getAuthors(): List<Author>
    suspend fun getRandomWork() : ResponseBody
    suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int
    ): SearchBookResponse
}