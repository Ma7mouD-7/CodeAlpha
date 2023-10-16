package com.ma7moud27.onlinebookshop.ui.main.fragments.categories.repository

import com.ma7moud27.onlinebookshop.utils.enums.Category

interface CategoriesRepository {
    fun getCategoryList(numOfItems: Int): List<Category>
}