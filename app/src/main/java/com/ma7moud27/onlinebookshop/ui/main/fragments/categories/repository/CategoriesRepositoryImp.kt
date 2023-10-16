package com.ma7moud27.onlinebookshop.ui.main.fragments.categories.repository

import com.ma7moud27.onlinebookshop.local.LocalDataSource
import com.ma7moud27.onlinebookshop.network.RemoteDataSource
import com.ma7moud27.onlinebookshop.utils.enums.Category

class CategoriesRepositoryImp(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : CategoriesRepository {
    override fun getCategoryList(numOfItems: Int): List<Category> =
        localDataSource.getCategoryList(numOfItems)
}
