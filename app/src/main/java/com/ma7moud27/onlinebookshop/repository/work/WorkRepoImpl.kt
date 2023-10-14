package com.ma7moud27.onlinebookshop.repository.work

import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.network.RemoteDataSource

class WorkRepoImpl(private val dataSource: RemoteDataSource) : WorkRepo {

    override suspend fun getWork(workID: String): Work = dataSource.getWork(workID).body()?:Work()

}