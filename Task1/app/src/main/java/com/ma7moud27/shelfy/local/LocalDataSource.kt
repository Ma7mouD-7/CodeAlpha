package com.ma7moud27.shelfy.local

import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.utils.enums.Category

interface LocalDataSource {
    fun getCategoryList(numOfItems: Int): List<Category>
    fun getTopAuthors(): List<Author>
}
