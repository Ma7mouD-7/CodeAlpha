package com.ma7moud27.shelfy.ui.login.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.ma7moud27.shelfy.network.firebase.FirebaseAuthClient

class LoginRepositoryImp : LoginRepository {
    override fun getCurrentUser() = FirebaseAuthClient.currentUser()

    override fun sendVerificationEmail() = FirebaseAuthClient.currentUser()?.sendEmailVerification()

    override suspend fun login(email: String, password: String): Task<AuthResult> =
        FirebaseAuthClient.login(email, password)
}
