package com.ma7moud27.exams.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseAuthClient {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    fun currentUser() = auth.currentUser
    fun login(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)
    fun register(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)
    fun logout() = auth.signOut()
    fun updateUserProfile(name: String) = auth.currentUser?.updateProfile(
        UserProfileChangeRequest.Builder().setDisplayName(name).build(),
    )
    fun sendVerificationEmail() = auth.currentUser?.sendEmailVerification()
}
