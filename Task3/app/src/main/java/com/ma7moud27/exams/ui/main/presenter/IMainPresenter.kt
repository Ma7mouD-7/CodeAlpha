package com.ma7moud27.exams.ui.main.presenter

import android.content.Context
import com.ma7moud27.exams.utils.enums.UserType

interface IMainPresenter {
    fun currentUser(userType: UserType)
    fun setProgressBarVisibility(isVisible: Boolean)
    fun setViews(userType: UserType)
    fun logout(context: Context)
}
