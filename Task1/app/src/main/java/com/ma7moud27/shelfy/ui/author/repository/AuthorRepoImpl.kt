package com.ma7moud27.shelfy.ui.author.repository

import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.network.RemoteDataSource

class AuthorRepoImpl(private val dataSource: RemoteDataSource) : AuthorRepo {

    override suspend fun getAuthor(authorID: String): Author =
        dataSource.getAuthor(authorID).body() ?: Author()
}
