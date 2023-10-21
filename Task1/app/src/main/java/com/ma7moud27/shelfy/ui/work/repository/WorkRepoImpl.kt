package com.ma7moud27.shelfy.ui.work.repository

import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.model.book.Book
import com.ma7moud27.shelfy.model.rating.Rating
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.network.RemoteDataSource
import com.ma7moud27.shelfy.network.firebase.FirebaseAuthClient
import com.ma7moud27.shelfy.network.firebase.FirebaseFirestoreClient

class WorkRepoImpl(private val dataSource: RemoteDataSource) : WorkRepo {

    override suspend fun getWork(workID: String): Work = dataSource.getWork(workID).body() ?: Work()
    override suspend fun getBook(bookID: String): Book = dataSource.getBook(bookID).body() ?: Book()

    override suspend fun getRatings(workID: String): Rating =
        dataSource.getRating(workID).body() ?: Rating()

    override suspend fun getUser(userID: String) = FirebaseFirestoreClient.getUser(userID)

    override fun getCurrentUser() = FirebaseAuthClient.currentUser()
    override suspend fun updateUser(userID: String, user: User) =
        FirebaseFirestoreClient.updateUser(userID, user)
}
