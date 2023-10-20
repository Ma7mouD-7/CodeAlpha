package com.ma7moud27.shelfy.network.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.utils.Constants

object FirebaseFirestoreClient {
    private val database: FirebaseFirestore by lazy { Firebase.firestore }

    fun addUser(userID: String, user: User) = database.collection(Constants.USERS_DATABASE).document(userID).set(user)
    fun getUser(userID: String) = database.collection(Constants.USERS_DATABASE).document(userID).get()
    fun updateUser(userID: String, user: User) = database.collection(Constants.USERS_DATABASE).document(userID).set(user, SetOptions.merge())
}
