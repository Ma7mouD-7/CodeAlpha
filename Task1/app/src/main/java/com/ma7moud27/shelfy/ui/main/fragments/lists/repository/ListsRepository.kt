package com.ma7moud27.shelfy.ui.main.fragments.lists.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.ma7moud27.shelfy.model.work.Work

interface ListsRepository {
    suspend fun getWork(workID: String): Work
    fun getCurrentUser(): FirebaseUser?
    fun getUser(userID: String): Task<DocumentSnapshot>
}
