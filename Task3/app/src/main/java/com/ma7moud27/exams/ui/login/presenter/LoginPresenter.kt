package com.ma7moud27.exams.ui.login.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.toObject
import com.ma7moud27.exams.local.UserDatabase
import com.ma7moud27.exams.model.CustomCallback
import com.ma7moud27.exams.model.User
import com.ma7moud27.exams.network.firebase.FirebaseAuthClient
import com.ma7moud27.exams.network.firebase.FirebaseFirestoreClient
import com.ma7moud27.exams.ui.login.view.LoginView
import java.util.concurrent.Executors

class LoginPresenter(private val view: LoginView) : ILoginPresenter {
    override fun doLogin(email: String, password: String) {
        if (view.validate()) {
            FirebaseAuthClient.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = FirebaseAuthClient.currentUser()
                    if (isVerified(currentUser)) {
                        view.cacheUser(currentUser!!)
                    } else {
                        view.onLoginResult(CustomCallback.Error(message = "Please verify your email first"))
                    }
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidUserException) {
                        view.onLoginResult(CustomCallback.Error(message = "User Not Found, Please Register First"))
                    } catch (e: Exception) {
                        task.exception?.message?.let {
                            if (it.contains("INVALID_LOGIN_CREDENTIALS")) {
                                view.onLoginResult(CustomCallback.Error(message = "Email or password is not correct, try again"))
                            } else if (it.contains("network error")) {
                                view.onLoginResult(CustomCallback.Error(message = "Connection Error:Please check your internet connection"))
                            } else {
                                view.onLoginResult(CustomCallback.Error(message = "Error in Logging you in, Please try again"))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setProgressBarVisibility(isVisible: Boolean) {
        view.onSetProgressBar(isVisible)
    }

    override fun insertUserToDb(context: Context, firebaseUser: FirebaseUser) {
        FirebaseFirestoreClient.getUser(firebaseUser.uid).addOnSuccessListener {
            val user = it.toObject<User>()
            if (user != null) {
                Executors.newSingleThreadExecutor().execute {
                    UserDatabase.getInstance(context).userDao().insert(user)
                }
            }
            view.onLoginResult(CustomCallback.Success(user!!))
        }
    }

    override fun isLoggedIn() {
        FirebaseAuthClient.currentUser().let {
            if (it != null) {
                FirebaseFirestoreClient.getUser(it.uid).addOnSuccessListener { document ->
                    Log.d("LOGIN_PROCESS", "isLoggedIn: ${document.toObject<User>()}")
                    view.onLoginResult(CustomCallback.Success(document.toObject<User>()!!))
                }
            }
        }
    }

    private fun sendVerificationEmail() {
        FirebaseAuthClient.sendVerificationEmail()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view.showToast("Verification email sent to your email", Toast.LENGTH_SHORT)
            } else {
                view.showToast(
                    task.exception?.message ?: "Error in Verification Email",
                    Toast.LENGTH_SHORT,
                )
            }
        }
    }

    private fun isVerified(currentUser: FirebaseUser?) =
        if (currentUser?.isEmailVerified != true) {
            AlertDialog.Builder(view as Context).setTitle("Your Email is not Verified")
                .setMessage("You need to verify your email address before you can proceed.")
                .setPositiveButton("Resend") { _, _ -> sendVerificationEmail() }
                .setNegativeButton("Cancel", null).setCancelable(false).show()
            false
        } else {
            true
        }
}
