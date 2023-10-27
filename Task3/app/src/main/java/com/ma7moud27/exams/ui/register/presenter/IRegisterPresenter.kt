package com.ma7moud27.exams.ui.register.presenter

import com.ma7moud27.exams.utils.enums.UserType

interface IRegisterPresenter {
    fun doRegister(email: String, password: String, name: String, type: UserType)
    fun setProgressBarVisibility(isVisible: Boolean)
}
