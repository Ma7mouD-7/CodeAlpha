package com.ma7moud27.onlinebookshop.local

import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.utils.enums.Category

interface LocalDataSource {
    fun getCategoryList(numOfItems: Int): List<Category>
    fun getTopAuthors(): List<Author>
}
