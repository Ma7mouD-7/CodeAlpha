package com.ma7moud27.shelfy.ui.register.viewmodel

import android.content.Intent
import android.util.Log
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
            Log.d("REGISTER_PROCESS", "launch ")
            repository.register(email, password).addOnCompleteListener { task ->
                Log.d("REGISTER_PROCESS", "addOnCompleteListener ")
                if (task.isSuccessful) {
                    Log.d("REGISTER_PROCESS", "isSuccessful get user")
                    val currentUser = repository.getCurrentUser()
                    Log.d("REGISTER_PROCESS", "isSuccessful update user ")
                    repository.updateUserProfile(currentUser!!, name).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("REGISTER_PROCESS", "isSuccessful updateProfile ")
                        } else {
                            Log.d("REGISTER_PROCESS", "failed updateProfile ")
                        }
                    }
                    Log.d("REGISTER_PROCESS", "isSuccessful handleAddToDatabase")
                    handleAddToDatabase(currentUser.uid, name, email)
                    Log.d("REGISTER_PROCESS", "isSuccessful handleVerificationEmail")
                    handleVerificationEmail(activity, email)
                } else {
                    Log.d("REGISTER_PROCESS", "isFailed")
                    Toast.makeText(
                        activity,
                        task.exception?.message,
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
        Log.d("REGISTER_PROCESS", "isSuccessful logout")
        repository.logout()
        activity.startActivity(
            Intent(activity, LoginActivity::class.java),
        )
        activity.finish()
    }

    private fun handleAddToDatabase(currentUser: String, name: String, email: String) {
        repository.addUser(currentUser, User(name, email)).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("REGISTER_PROCESS", "isSuccessful AddToDatabase ")
            } else {
                Log.d("REGISTER_PROCESS", "failed AddToDatabase ")
            }
        }
    }

    private fun handleVerificationEmail(activity: AppCompatActivity, email: String) {
        Log.d("REGISTER_PROCESS", "handleVerificationEmail ")
        repository.sendVerificationEmail()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Verification email sent to $email", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, task.exception?.message ?: "Error in Verification Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
