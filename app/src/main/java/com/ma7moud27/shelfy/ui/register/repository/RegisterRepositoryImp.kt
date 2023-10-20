package com.ma7moud27.shelfy.ui.register.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.network.firebase.FirebaseAuthClient
import com.ma7moud27.shelfy.network.firebase.FirebaseFirestoreClient

class RegisterRepositoryImp : RegisterRepository {

    override fun getCurrentUser() = FirebaseAuthClient.currentUser()

    override fun sendVerificationEmail() = FirebaseAuthClient.currentUser()?.sendEmailVerification()

    override suspend fun register(email: String, password: String): Task<AuthResult> =
        FirebaseAuthClient.register(email, password)

    override fun updateUserProfile(currentUser: FirebaseUser, name: String) =
        currentUser.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build(),
        )

    override fun logout() = FirebaseAuthClient.logout()

    override fun addUser(userID: String, user: User) = FirebaseFirestoreClient.addUser(userID, user)
}
