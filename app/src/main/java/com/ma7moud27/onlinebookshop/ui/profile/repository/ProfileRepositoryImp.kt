package com.ma7moud27.onlinebookshop.ui.profile.repository

import com.ma7moud27.onlinebookshop.local.LocalDataSource
import com.ma7moud27.onlinebookshop.network.RemoteDataSource

class ProfileRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : ProfileRepository {

}
