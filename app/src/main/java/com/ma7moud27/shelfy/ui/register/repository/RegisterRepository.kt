package com.ma7moud27.shelfy.ui.register.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.ma7moud27.shelfy.model.User

interface RegisterRepository {
    fun getCurrentUser(): FirebaseUser?
    fun sendVerificationEmail(): Task<Void>?
    suspend fun register(email: String, password: String): Task<AuthResult>
    fun updateUserProfile(currentUser: FirebaseUser, name: String): Task<Void>
    fun logout()
    fun addUser(userID: String, user: User): Task<Void>
}
