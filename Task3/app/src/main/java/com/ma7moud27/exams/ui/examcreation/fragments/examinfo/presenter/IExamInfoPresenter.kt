package com.ma7moud27.exams.ui.examcreation.fragments.examinfo.presenter

interface IExamInfoPresenter {
    fun initExam(
        code: String,
        name: String,
        title: String,
        duration: Int,
        finalGrade: Int,
    )
}
