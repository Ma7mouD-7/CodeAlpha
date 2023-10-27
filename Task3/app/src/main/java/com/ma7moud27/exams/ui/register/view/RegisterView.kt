package com.ma7moud27.exams.ui.register.view

import com.ma7moud27.exams.model.CustomCallback

interface RegisterView {
    fun onRegisterResult(callback: CustomCallback<String>)
    fun onSetProgressBar(isVisible: Boolean)
    fun validate(): Boolean
    fun showToast(message: String, length: Int)
}
