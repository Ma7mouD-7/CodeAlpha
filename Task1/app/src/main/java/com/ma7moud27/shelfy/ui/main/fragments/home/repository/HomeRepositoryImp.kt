package com.ma7moud27.shelfy.ui.main.fragments.home.repository

import com.ma7moud27.shelfy.local.LocalDataSource
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.network.RemoteDataSource
import com.ma7moud27.shelfy.network.firebase.FirebaseAuthClient
import com.ma7moud27.shelfy.utils.enums.Category
import okhttp3.ResponseBody

class HomeRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : HomeRepository {
    override fun getCategoryList(numOfItems: Int): List<Category> =
        localDataSource.getCategoryList(numOfItems)

    override suspend fun getAuthors(): List<Author> = localDataSource.getTopAuthors()

    override suspend fun getTrending(trendTime: String, page: Int, limit: Int): SearchBookResponse =
        remoteDataSource.getTrending(trendTime, page, limit).body() ?: SearchBookResponse()

    override suspend fun getWork(workID: String): Work =
        remoteDataSource.getWork(workID).body() ?: Work()

    override suspend fun getRandomWork(): ResponseBody = remoteDataSource.getRandomWork()
    override suspend fun searchBooks(
        query: String,
        mode: String,
        page: Int,
        isFullText: Boolean,
        sort: String,
        language: String,
        limit: Int,
    ): SearchBookResponse =
        remoteDataSource.searchBooks(query, mode, page, isFullText, sort, language, limit).body()
            ?: SearchBookResponse()

    override fun logout() = FirebaseAuthClient.logout()
    override fun currentUser() = FirebaseAuthClient.currentUser()
}
