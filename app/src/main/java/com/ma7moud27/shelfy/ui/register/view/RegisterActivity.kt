package com.ma7moud27.shelfy.ui.register.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.ui.login.view.LoginActivity
import com.ma7moud27.shelfy.ui.register.repository.RegisterRepositoryImp
import com.ma7moud27.shelfy.ui.register.viewmodel.RegisterViewModel
import com.ma7moud27.shelfy.ui.register.viewmodel.RegisterViewModelFactory
import com.ma7moud27.shelfy.utils.Validator

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signInTextView: TextView
    private lateinit var registerButton: Button

    lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponents()
        prepareViewModel()

        registerButton.setOnClickListener {
            if (Validator.isFormValid(
                    listOf(
                        nameEditText,
                        emailEditText,
                        passwordEditText,
                        confirmPasswordEditText,
                    ),
                ) &&
                Validator.isNameValid(nameEditText) &&
                Validator.isEmailValid(emailEditText) &&
                Validator.isPasswordValid(passwordEditText) &&
                Validator.isPasswordMatch(passwordEditText, confirmPasswordEditText)
            ) {
                viewModel.register(
                    this,
                    nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                )
            }
        }
        signInTextView.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java),
            )
            finish()
        }
    }

    private fun initComponents() {
        nameEditText = findViewById(R.id.register_name_et)
        emailEditText = findViewById(R.id.register_email_et)
        passwordEditText = findViewById(R.id.register_password_et)
        confirmPasswordEditText = findViewById(R.id.register_confirm_password_et)
        signInTextView = findViewById(R.id.register_signin_tv)
        registerButton = findViewById(R.id.register_submit_btn)
    }

    private fun prepareViewModel() {
        val repository = RegisterRepositoryImp()
        val viewModelFactory = RegisterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }
}
