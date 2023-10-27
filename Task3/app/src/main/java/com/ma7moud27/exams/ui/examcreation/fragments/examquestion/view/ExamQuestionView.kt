package com.ma7moud27.exams.ui.examcreation.fragments.examquestion.view

import com.ma7moud27.exams.model.Question

interface ExamQuestionView {

    fun createQuestionObject(): Question
    fun clearFields()
    fun setFields(
        question: String,
        points: String,
        option: String,
        optionText: String,
        type: Int,
    )

    fun updateViews(
        placeholder: Int,
        hint: Int,
        minLines: Int,
        maxEms: Int,
    )
}
