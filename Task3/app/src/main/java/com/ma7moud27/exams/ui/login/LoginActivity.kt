package com.ma7moud27.exams.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.toObject
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.CustomCallback
import com.ma7moud27.exams.model.User
import com.ma7moud27.exams.network.firebase.FirebaseAuthClient
import com.ma7moud27.exams.ui.login.presenter.LoginPresenter
import com.ma7moud27.exams.ui.login.view.LoginView
import com.ma7moud27.exams.ui.main.MainActivity
import com.ma7moud27.exams.ui.register.RegisterActivity
import com.ma7moud27.exams.utils.Constants.Companion.USER_TYPE
import com.ma7moud27.exams.utils.UtilMethods
import com.ma7moud27.exams.utils.Validator

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    private lateinit var progressbar: AlertDialog

    private lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_login)
        initComponents()

        loginButton.setOnClickListener {
            presenter.setProgressBarVisibility(true)
            presenter.doLogin(
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
            )
        }

        signUpTextView.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java),
            )
            finish()
        }
    }

    private fun initComponents() {
        emailEditText = findViewById(R.id.login_email_et)
        passwordEditText = findViewById(R.id.login_password_et)
        forgotPasswordTextView = findViewById(R.id.login_forgot_tv)
        signUpTextView = findViewById(R.id.login_signup_tv)
        loginButton = findViewById(R.id.login_submit_btn)

        progressbar =
            UtilMethods.showProgressbar(this, getString(R.string.logging_you_in))
        presenter = LoginPresenter(this)
        presenter.isLoggedIn()
    }

    override fun onLoginResult(callback: CustomCallback<User>) {
        when (callback) {
            is CustomCallback.Success -> {
                Log.d("LOGIN_PROCESS", "isLoggedIn: ${callback.data!!.type}")
                Log.d("LOGIN_PROCESS", "isLoggedIn: ${(callback.data!!.type!!)::class.java.simpleName}")
                Log.d("LOGIN_PROCESS", "isLoggedIn: ${callback.data!!.type!!.name}")
                Log.d("LOGIN_PROCESS", "isLoggedIn: ${(callback.data!!.type!!.name)::class.java.simpleName}")
                startActivity(
                    Intent(this, MainActivity::class.java).apply {
                        putExtra(USER_TYPE, callback.data!!.type!!.name)
                    },
                )
                finish()
            }
            else -> showToast(callback.message ?: "", Toast.LENGTH_LONG)
        }
        presenter.setProgressBarVisibility(false)
    }

    override fun onSetProgressBar(isVisible: Boolean) {
        when (isVisible) {
            true -> progressbar.show()
            false -> progressbar.dismiss()
        }
    }

    override fun validate(): Boolean = Validator.isFormValid(listOf(emailEditText, passwordEditText))
    override fun showToast(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }

    override fun cacheUser(currentUser: FirebaseUser) {
        presenter.insertUserToDb(this, currentUser)
    }
}
