package com.ma7moud27.exams.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.CustomCallback
import com.ma7moud27.exams.ui.login.LoginActivity
import com.ma7moud27.exams.ui.register.presenter.RegisterPresenter
import com.ma7moud27.exams.ui.register.view.RegisterView
import com.ma7moud27.exams.utils.UtilMethods
import com.ma7moud27.exams.utils.Validator
import com.ma7moud27.exams.utils.enums.UserType

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var signInTextView: TextView
    private lateinit var userTypeGroup: RadioGroup

    private lateinit var progressbar: AlertDialog

    private lateinit var presenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponent()

        signupButton.setOnClickListener {
            presenter.setProgressBarVisibility(true)
            presenter.doRegister(
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                nameEditText.text.toString(),
                when (userTypeGroup.checkedRadioButtonId) {
                    R.id.login_taker_radio_btn -> UserType.TAKER
                    else -> UserType.CREATOR
                },
            )
        }
        signInTextView.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java),
            )
            finish()
        }
    }

    private fun initComponent() {
        nameEditText = findViewById(R.id.register_name_et)
        emailEditText = findViewById(R.id.register_email_et)
        passwordEditText = findViewById(R.id.register_password_et)
        confirmPasswordEditText = findViewById(R.id.register_confirm_password_et)
        signInTextView = findViewById(R.id.register_login_tv)
        signupButton = findViewById(R.id.register_submit_btn)
        userTypeGroup = findViewById(R.id.register_user_type_group)

        progressbar = UtilMethods.showProgressbar(this, getString(R.string.loading))

        presenter = RegisterPresenter(this)
    }

    override fun onRegisterResult(callback: CustomCallback<String>) {
        when (callback) {
            is CustomCallback.Success -> {
                startActivity(
                    Intent(this, LoginActivity::class.java),
                )
                finish()
            }
            else -> showToast(callback.data ?: "", Toast.LENGTH_LONG)
        }
        presenter.setProgressBarVisibility(false)
    }
    override fun onSetProgressBar(isVisible: Boolean) {
        when (isVisible) {
            true -> progressbar.show()
            false -> progressbar.dismiss()
        }
    }
    override fun validate(): Boolean =
        Validator.isFormValid(listOf(emailEditText, passwordEditText)) &&
            Validator.isNameValid(nameEditText) &&
            Validator.isEmailValid(emailEditText) &&
            Validator.isPasswordValid(passwordEditText) &&
            Validator.isPasswordMatch(passwordEditText, confirmPasswordEditText)
    override fun showToast(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }
}
