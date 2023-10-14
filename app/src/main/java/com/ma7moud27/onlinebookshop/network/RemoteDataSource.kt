package com.ma7moud27.onlinebookshop.network

import com.ma7moud27.onlinebookshop.model.BookShelf
import com.ma7moud27.onlinebookshop.model.SearchAuthorResponse
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.model.rating.Rating
import com.ma7moud27.onlinebookshop.model.work.Work
import okhttp3.Call
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getAuthor(authorId: String): Response<Author>
    suspend fun getWork(workId: String): Response<Work>
    suspend fun getRating(workId: String): Response<Rating>
    suspend fun getBookShelf(workId: String): Response<BookShelf>
    suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int
    ): Response<SearchBookResponse>

    suspend fun searchAuthors(
        query: String,
        page: Int,
        sort: String,
    ): Response<SearchAuthorResponse>

    suspend fun getTrending(
        trendTime: String,
        page: Int,
        limit: Int
    ): Response<SearchBookResponse>

    suspend fun getRandomWork() : ResponseBody
}