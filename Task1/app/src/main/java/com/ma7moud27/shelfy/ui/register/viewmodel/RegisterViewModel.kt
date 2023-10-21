package com.ma7moud27.shelfy.ui.register.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.ui.login.view.LoginActivity
import com.ma7moud27.shelfy.ui.register.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    fun register(activity: AppCompatActivity, name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = repository.getCurrentUser()
                    repository.updateUserProfile(currentUser!!, name)
                    handleAddToDatabase(currentUser.uid, name, email)
                    handleVerificationEmail(activity, email)
                } else {
                    Toast.makeText(
                        activity,
                        task.exception?.message,
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
        repository.logout()
        activity.startActivity(
            Intent(activity, LoginActivity::class.java),
        )
        activity.finish()
    }

    private fun handleAddToDatabase(currentUser: String, name: String, email: String) {
        repository.addUser(currentUser, User(name, email))
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
}
