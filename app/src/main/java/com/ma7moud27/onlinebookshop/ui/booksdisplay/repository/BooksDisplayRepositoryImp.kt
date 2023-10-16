package com.ma7moud27.onlinebookshop.ui.booksdisplay.repository

import com.ma7moud27.onlinebookshop.local.LocalDataSource
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.network.RemoteDataSource
import com.ma7moud27.onlinebookshop.utils.enums.Category

class BooksDisplayRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : BooksDisplayRepository {
    override suspend fun getTrending(trendTime: String, page: Int, limit: Int): SearchBookResponse =
        remoteDataSource.getTrending(trendTime, page, limit).body() ?: SearchBookResponse()

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
    override fun getCategoryList(numOfItems: Int): List<Category> = localDataSource.getCategoryList(numOfItems)
    override suspend fun getWork(workID: String): Work = remoteDataSource.getWork(workID).body() ?: Work()

}
