package com.ma7moud27.shelfy.ui.main.fragments.lists.repository

import com.ma7moud27.shelfy.local.LocalDataSource
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.network.RemoteDataSource
import com.ma7moud27.shelfy.network.firebase.FirebaseAuthClient
import com.ma7moud27.shelfy.network.firebase.FirebaseFirestoreClient

class ListsRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : ListsRepository {
    override suspend fun getWork(workID: String): Work = remoteDataSource.getWork(workID).body() ?: Work()
    override fun getCurrentUser() = FirebaseAuthClient.currentUser()

    override fun getUser(userID: String) = FirebaseFirestoreClient.getUser(userID)
}
