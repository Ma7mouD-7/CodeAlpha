package com.ma7moud27.shelfy.ui.profile.repository

import com.ma7moud27.shelfy.local.LocalDataSource
import com.ma7moud27.shelfy.network.RemoteDataSource

class ProfileRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : ProfileRepository {

}
