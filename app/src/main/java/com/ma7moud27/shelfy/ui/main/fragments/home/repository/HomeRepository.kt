package com.ma7moud27.shelfy.ui.main.fragments.home.repository

import com.google.firebase.auth.FirebaseUser
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.utils.enums.Category
import okhttp3.ResponseBody

interface HomeRepository {
    fun getCategoryList(numOfItems: Int): List<Category>
    suspend fun getTrending(
        trendTime: String,
        page: Int,
        limit: Int,
    ): SearchBookResponse
    suspend fun getWork(workID: String): Work
    suspend fun getAuthors(): List<Author>
    suspend fun getRandomWork(): ResponseBody
    suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int,
    ): SearchBookResponse
    fun logout()
    fun currentUser(): FirebaseUser?
}
