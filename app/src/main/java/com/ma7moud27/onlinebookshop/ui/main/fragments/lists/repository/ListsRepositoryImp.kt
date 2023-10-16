package com.ma7moud27.onlinebookshop.ui.main.fragments.lists.repository

import com.ma7moud27.onlinebookshop.local.LocalDataSource
import com.ma7moud27.onlinebookshop.network.RemoteDataSource

class ListsRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : ListsRepository {

}