package com.ma7moud27.onlinebookshop.repository.author

import com.ma7moud27.onlinebookshop.model.author.Author

interface AuthorRepo {
    suspend fun getAuthor(authorID:String): Author
}
