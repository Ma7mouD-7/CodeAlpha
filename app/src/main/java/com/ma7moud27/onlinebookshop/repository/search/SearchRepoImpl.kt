package com.ma7moud27.onlinebookshop.repository.search

import com.ma7moud27.onlinebookshop.model.SearchAuthorResponse
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.network.RemoteDataSource
import retrofit2.Response

class SearchRepoImpl(private val dataSource: RemoteDataSource) : SearchRepo {

    override suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int
    ): SearchBookResponse = dataSource.searchBooks(query, mode, page,isFullText, sort, language,
        limit).body()?: SearchBookResponse()

    override suspend fun searchAuthors(
        query: String,
        page: Int,
        sort: String
    ): SearchAuthorResponse = dataSource.searchAuthors(query, page, sort).body()?: SearchAuthorResponse()


}