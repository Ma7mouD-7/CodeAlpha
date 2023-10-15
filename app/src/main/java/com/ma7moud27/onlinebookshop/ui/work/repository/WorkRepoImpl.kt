package com.ma7moud27.onlinebookshop.ui.work.repository

import com.ma7moud27.onlinebookshop.model.book.Book
import com.ma7moud27.onlinebookshop.model.rating.Rating
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.network.RemoteDataSource

class WorkRepoImpl(private val dataSource: RemoteDataSource) : WorkRepo {

    override suspend fun getWork(workID: String): Work = dataSource.getWork(workID).body() ?: Work()
    override suspend fun getBook(bookID: String): Book = dataSource.getBook(bookID).body() ?: Book()

    override suspend fun getRatings(workID: String): Rating =
        dataSource.getRating(workID).body() ?: Rating()
}
