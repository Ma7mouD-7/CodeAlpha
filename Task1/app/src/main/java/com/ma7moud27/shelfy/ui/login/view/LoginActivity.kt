package com.ma7moud27.shelfy.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.ui.login.repository.LoginRepositoryImp
import com.ma7moud27.shelfy.ui.login.viewmodel.LoginViewModel
import com.ma7moud27.shelfy.ui.login.viewmodel.LoginViewModelFactory
import com.ma7moud27.shelfy.ui.main.MainActivity
import com.ma7moud27.shelfy.ui.register.view.RegisterActivity
import com.ma7moud27.shelfy.utils.Validator

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpTextView: TextView
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var loginButton: Button

    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        installSplashScreen()
        setContentView(R.layout.activity_login)
        initComponents()
        prepareViewModel()

        if (viewModel.getUser() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginButton.setOnClickListener {
            if (Validator.isFormValid(
                    listOf(
                        emailEditText,
                        passwordEditText,
                    ),
                )
            ) {
                viewModel.login(
                    this,
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                )
            }
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
        signUpTextView = findViewById(R.id.login_signup_tv)
        forgotPasswordTextView = findViewById(R.id.login_forgot_tv)
        loginButton = findViewById(R.id.login_submit_btn)
    }

    private fun prepareViewModel() {
        val repository = LoginRepositoryImp()
        val viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }
}
