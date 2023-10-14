package com.ma7moud27.onlinebookshop.ui.main.fragments.home.repository

import com.ma7moud27.onlinebookshop.local.LocalDataSource
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.network.RemoteDataSource
import com.ma7moud27.onlinebookshop.utils.enums.Category
import okhttp3.ResponseBody

class HomeRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HomeRepository {
    override fun getCategoryList(numOfItems: Int): List<Category> = Category.values().take(numOfItems)

    override suspend fun getTrending(trendTime: String, page: Int, limit: Int): SearchBookResponse =
        remoteDataSource.getTrending(trendTime, page, limit).body() ?: SearchBookResponse()

    override suspend fun getWork(workID: String): Work = remoteDataSource.getWork(workID).body() ?: Work()

    override suspend fun getAuthors(): List<Author> = localDataSource.getTopAuthors()
    override suspend fun getRandomWork(): ResponseBody = remoteDataSource.getRandomWork()
    override suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int
    ): SearchBookResponse = remoteDataSource.searchBooks(query, mode, page,isFullText, sort, language,limit).body()?: SearchBookResponse()


}