package com.ma7moud27.shelfy.network.openlibrary

import com.ma7moud27.shelfy.model.BookShelf
import com.ma7moud27.shelfy.model.SearchAuthorResponse
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.model.book.Book
import com.ma7moud27.shelfy.model.rating.Rating
import com.ma7moud27.shelfy.model.work.Work
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibApiService { // https://openlibrary.org/

    @GET("authors/{authorId}.json")
    suspend fun authorResponse(@Path("authorId") authorId: String): Response<Author>

    @GET("works/{workId}.json")
    suspend fun workResponse(@Path("workId") workId: String): Response<Work>

    @GET("books/{bookId}.json")
    suspend fun bookResponse(@Path("bookId") bookId: String): Response<Book>

    @GET("works/{workId}/ratings.json")
    suspend fun ratingResponse(@Path("workId") workId: String): Response<Rating>

    @GET("works/{workId}/bookshelves.json")
    suspend fun bookShelfResponse(@Path("workId") workId: String): Response<BookShelf>

    @GET("search.json")
    suspend fun searchBooksResponse(
        @Query("q") query: String,
        @Query("mode") mode: String,
        @Query("page") page: Int,
        @Query("has_fulltext") isFullText: Boolean,
        @Query("sort") sort: String,
        @Query("language") language: String,
        @Query("limit") limit: Int,

    ): Response<SearchBookResponse>

    @GET("search/authors.json")
    suspend fun searchAuthorsResponse(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String,
    ): Response<SearchAuthorResponse>

    @GET("trending/{trendTime}.json")
    suspend fun trendingResponse(
        @Path("trendTime") trendTime: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<SearchBookResponse>

    @GET("random")
    suspend fun getRandomWork(): ResponseBody
}
