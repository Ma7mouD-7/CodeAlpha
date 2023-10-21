package com.ma7moud27.shelfy.ui.login.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface LoginRepository {
    fun getCurrentUser(): FirebaseUser?
    fun sendVerificationEmail(): Task<Void>?
    suspend fun login(email: String, password: String): Task<AuthResult>
}
