package com.ma7moud27.shelfy.ui.main.fragments.categories.repository

import com.ma7moud27.shelfy.local.LocalDataSource
import com.ma7moud27.shelfy.utils.enums.Category

class CategoriesRepositoryImp(
    private val localDataSource: LocalDataSource,
) : CategoriesRepository {
    override fun getCategoryList(numOfItems: Int): List<Category> =
        localDataSource.getCategoryList(numOfItems)
}
