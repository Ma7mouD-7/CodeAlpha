package com.ma7moud27.shelfy.ui.login.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.ma7moud27.shelfy.ui.login.repository.LoginRepository
import com.ma7moud27.shelfy.ui.main.MainActivity
import com.ma7moud27.shelfy.utils.Constants
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun getUser() = repository.getCurrentUser()

    private fun isVerified(activity: AppCompatActivity, currentUser: FirebaseUser?, email: String) =
        if (currentUser?.isEmailVerified != true) {
            AlertDialog.Builder(activity).setTitle("Your Email is not Verified")
                .setMessage("You need to verify your email address before you can proceed.")
                .setPositiveButton("Resend") { _, _ -> handleVerificationEmail(activity, email) }
                .setNegativeButton("Cancel", null).setCancelable(false).show()
            false
        } else {
            true
        }

    private fun handleVerificationEmail(activity: AppCompatActivity, email: String) {
        repository.sendVerificationEmail()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Verification email sent to $email", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    activity,
                    task.exception?.message ?: "Error in Verification Email",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    fun login(activity: AppCompatActivity, email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = repository.getCurrentUser()
                    if (isVerified(activity, currentUser, email)) {
                        activity.startActivity(
                            Intent(activity, MainActivity::class.java).apply {
                                putExtra(
                                    Constants.USER_NAME,
                                    currentUser?.displayName,
                                )
                            },
                        )
                        activity.finish()
                    }
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidUserException) {
                        Toast.makeText(
                            activity,
                            "User Not Found, Please Register First",
                            Toast.LENGTH_LONG,
                        ).show()
                    } catch (e: Exception) {
                        task.exception?.message?.let {
                            if (it.contains("INVALID_LOGIN_CREDENTIALS")) {
                                Toast.makeText(
                                    activity,
                                    "Email or password is not correct, try again",
                                    Toast.LENGTH_LONG,
                                ).show()
                            } else if (it.contains("network error")) {
                                Toast.makeText(
                                    activity,
                                    "Connection Error:Please check your internet connection",
                                    Toast.LENGTH_LONG,
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Error in Logging you in, Please try again",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
