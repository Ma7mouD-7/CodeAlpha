package com.ma7moud27.onlinebookshop.ui.author.repository

import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.network.RemoteDataSource

class AuthorRepoImpl(private val dataSource: RemoteDataSource) : AuthorRepo {

    override suspend fun getAuthor(authorID: String): Author =
        dataSource.getAuthor(authorID).body() ?: Author()
}
