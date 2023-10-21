package com.ma7moud27.shelfy.ui.author.repository

import com.ma7moud27.shelfy.model.author.Author

fun interface AuthorRepo {
    suspend fun getAuthor(authorID: String): Author
}
