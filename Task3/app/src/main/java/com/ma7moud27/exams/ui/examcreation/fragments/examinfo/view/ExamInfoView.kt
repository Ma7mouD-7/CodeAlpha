package com.ma7moud27.exams.ui.examcreation.fragments.examinfo.view

interface ExamInfoView {
    fun clearFields()
    fun setFields(
        code: String,
        title: String,
        name: String,
        duration: String,
        finalGrade: String,
    )
}
