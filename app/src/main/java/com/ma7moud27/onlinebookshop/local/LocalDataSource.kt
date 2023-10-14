package com.ma7moud27.onlinebookshop.local

import com.ma7moud27.onlinebookshop.model.author.Author

interface LocalDataSource {
    fun getTopAuthors() : List<Author>
}