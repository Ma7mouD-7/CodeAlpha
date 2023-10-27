package com.ma7moud27.exams.utils

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern

object Validator {
    fun isFormValid(mList: List<EditText>): Boolean {
        var valid = true
        for (et in mList) {
            val content = et.text.toString()
            if (TextUtils.isEmpty(content)) {
                et.error = "Required."
                valid = false
            } else {
                et.error = null
            }
        }
        return valid
    }

    fun isEmailValid(email: EditText): Boolean =
        email.text.toString().let {
            if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                email.error = "Not a Valid Email, (example@example.com)"
                false
            } else {
                email.error = null
                true
            }
        }

    fun isPasswordValid(password: EditText): Boolean =
        password.text.toString().let {
            if (!Pattern
                    .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>\\.]).{8,64}$")
                    .matcher(it)
                    .matches()
            ) {
                password.error =
                    "Password must be a length of at least 8 characters\n-contains at least one digit\n-at least one lowercase character\n-at least one uppercase character\n-and at least one special character"
                false
            } else {
                password.error = null
                true
            }
        }

    fun isNameValid(name: EditText): Boolean =
        name.text.toString().let {
            if (it.isEmpty()) {
                name.error = "Name is required"
                false
            } else if (!it.matches(Regex("^[\\p{L} .'-]+$"))) {
                name.error = "Name contains invalid characters"
                false
            } else {
                name.error = null
                true
            }
        }

    fun isPasswordMatch(password: EditText, confirm: EditText): Boolean =
        if (confirm.text.toString() != password.text.toString()) {
            confirm.error = "Passwords Don't Match"
            false
        } else {
            confirm.error = null
            true
        }
}
