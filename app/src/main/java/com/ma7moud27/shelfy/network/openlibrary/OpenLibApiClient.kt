package com.ma7moud27.shelfy.network.openlibrary

import com.ma7moud27.shelfy.model.BookShelf
import com.ma7moud27.shelfy.model.SearchAuthorResponse
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.model.book.Book
import com.ma7moud27.shelfy.model.rating.Rating
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.network.RemoteDataSource
import okhttp3.ResponseBody
import retrofit2.Response

object OpenLibApiClient : RemoteDataSource {
    override suspend fun getAuthor(authorId: String): Response<Author> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).authorResponse(authorId)

    override suspend fun getWork(workId: String): Response<Work> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).workResponse(workId)

    override suspend fun getBook(bookId: String): Response<Book> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).bookResponse(bookId)

    override suspend fun getRating(workId: String): Response<Rating> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).ratingResponse(workId)

    override suspend fun getBookShelf(workId: String): Response<BookShelf> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).bookShelfResponse(workId)

    override suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int,
    ): Response<SearchBookResponse> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java)
            .searchBooksResponse(query, mode, page, isFullText, sort, language, limit)

    override suspend fun searchAuthors(
        query: String,
        page: Int,
        sort: String,
    ): Response<SearchAuthorResponse> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java)
            .searchAuthorsResponse(query, page, sort)

    override suspend fun getTrending(
        trendTime: String,
        page: Int,
        limit: Int,
    ): Response<SearchBookResponse> =
        OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).trendingResponse(trendTime, page, limit)

    override suspend fun getRandomWork(): ResponseBody = OpenLibRetrofit.retrofit.create(OpenLibApiService::class.java).getRandomWork()
}
