package com.ma7moud27.onlinebookshop.ui.login.repository

class LoginRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : LoginRepository {

}