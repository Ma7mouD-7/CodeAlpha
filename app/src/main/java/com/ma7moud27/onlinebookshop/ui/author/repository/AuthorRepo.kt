package com.ma7moud27.onlinebookshop.ui.author.repository

import com.ma7moud27.onlinebookshop.model.author.Author

interface AuthorRepo {
    suspend fun getAuthor(authorID: String): Author
}
