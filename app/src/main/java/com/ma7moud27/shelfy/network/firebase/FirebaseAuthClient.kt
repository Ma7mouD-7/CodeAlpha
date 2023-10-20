package com.ma7moud27.shelfy.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseAuthClient {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    fun currentUser() = auth.currentUser
    fun login(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)
    fun register(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)
    fun logout() = auth.signOut()
}
