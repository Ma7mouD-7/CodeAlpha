package com.ma7moud27.exams.ui.login.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.ma7moud27.exams.utils.enums.UserType

interface ILoginPresenter {
    fun doLogin(email: String, password: String)
    fun setProgressBarVisibility(isVisible: Boolean)
    fun insertUserToDb(context: Context, firebaseUser: FirebaseUser)
    fun isLoggedIn()
}
