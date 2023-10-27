package com.ma7moud27.exams.ui.main.view

import com.ma7moud27.exams.model.CustomCallback
import com.ma7moud27.exams.model.User

interface MainView {
    fun getUser(callback: CustomCallback<User>)
    fun <T> setDataToViews(
        name: String,
        totalString: Int,
        average: Double,
        topExams: List<T>,
        history: List<T>,
    )

    fun setViews(takerVisibility: Int, creatorVisibility: Int)

    fun onSetProgressBar(isVisible: Boolean)
    fun logout()

}
