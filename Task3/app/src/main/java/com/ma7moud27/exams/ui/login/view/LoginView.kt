package com.ma7moud27.exams.ui.login.view

import com.google.firebase.auth.FirebaseUser
import com.ma7moud27.exams.model.CustomCallback
import com.ma7moud27.exams.model.User
import com.ma7moud27.exams.utils.enums.UserType

interface LoginView {
    fun onLoginResult(callback: CustomCallback<User>)
    fun onSetProgressBar(isVisible: Boolean)
    fun validate(): Boolean
    fun showToast(message: String, length: Int)
    fun cacheUser(currentUser: FirebaseUser)
}
