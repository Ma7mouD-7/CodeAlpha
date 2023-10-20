package com.ma7moud27.shelfy.ui.main.fragments.search.repository

import com.ma7moud27.shelfy.model.SearchAuthorResponse
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.network.RemoteDataSource

class SearchRepoImpl(private val remoteDataSource: RemoteDataSource) : SearchRepo {

    override suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int,
    ): SearchBookResponse = remoteDataSource.searchBooks(
        query,
        mode,
        page,
        isFullText,
        sort,
        language,
        limit,
    ).body() ?: SearchBookResponse()

    override suspend fun searchAuthors(
        query: String,
        page: Int,
        sort: String,
    ): SearchAuthorResponse = remoteDataSource.searchAuthors(query, page, sort).body() ?: SearchAuthorResponse()

    override suspend fun getWork(workID: String): Work = remoteDataSource.getWork(workID).body() ?: Work()

}
