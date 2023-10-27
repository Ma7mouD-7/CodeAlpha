package com.ma7moud27.exams.ui.register.presenter

import android.widget.Toast
import com.ma7moud27.exams.model.CustomCallback
import com.ma7moud27.exams.model.User
import com.ma7moud27.exams.network.firebase.FirebaseAuthClient
import com.ma7moud27.exams.network.firebase.FirebaseFirestoreClient
import com.ma7moud27.exams.ui.register.view.RegisterView
import com.ma7moud27.exams.utils.enums.UserType

class RegisterPresenter(private val view: RegisterView) : IRegisterPresenter {
    override fun doRegister(email: String, password: String, name: String, type: UserType) {
        if (view.validate()) {
            FirebaseAuthClient.register(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = FirebaseAuthClient.currentUser()
                    FirebaseAuthClient.updateUserProfile(name)
                    FirebaseFirestoreClient.addUser(
                        currentUser!!.uid,
                        User(
                            currentUser.uid,
                            name,
                            email,
                            type,
                        ),
                    )
                    sendVerificationEmail()
                    FirebaseAuthClient.logout()
                    view.onRegisterResult(CustomCallback.Success(""))
                } else {
                    view.onRegisterResult(CustomCallback.Error(task.exception?.message ?: "Error in Verification Email", ""))
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
    override fun setProgressBarVisibility(isVisible: Boolean) {
        view.onSetProgressBar(isVisible)
    }
}
