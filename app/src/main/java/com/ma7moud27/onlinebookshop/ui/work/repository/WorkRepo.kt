package com.ma7moud27.onlinebookshop.ui.work.repository

import com.ma7moud27.onlinebookshop.model.book.Book
import com.ma7moud27.onlinebookshop.model.rating.Rating
import com.ma7moud27.onlinebookshop.model.work.Work

interface WorkRepo {
    suspend fun getWork(workID: String): Work
    suspend fun getBook(bookID: String): Book
    suspend fun getRatings(workID: String): Rating
}
