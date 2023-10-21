package com.ma7moud27.shelfy.ui.main.fragments.categories.repository

import com.ma7moud27.shelfy.utils.enums.Category

fun interface CategoriesRepository {
    fun getCategoryList(numOfItems: Int): List<Category>
}
