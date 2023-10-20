package com.ma7moud27.shelfy.ui.work.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.model.book.Book
import com.ma7moud27.shelfy.model.rating.Rating
import com.ma7moud27.shelfy.model.work.Work

interface WorkRepo {
    suspend fun getWork(workID: String): Work
    suspend fun getBook(bookID: String): Book
    suspend fun getRatings(workID: String): Rating
    suspend fun getUser(userID: String): Task<DocumentSnapshot>
    fun getCurrentUser(): FirebaseUser?
    suspend fun updateUser(userID: String, user: User): Task<Void>
}
