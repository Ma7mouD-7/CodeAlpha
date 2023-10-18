package com.ma7moud27.onlinebookshop.ui.login.repository

import com.ma7moud27.onlinebookshop.local.LocalDataSource
import com.ma7moud27.onlinebookshop.network.RemoteDataSource

class LoginRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : LoginRepository {

}