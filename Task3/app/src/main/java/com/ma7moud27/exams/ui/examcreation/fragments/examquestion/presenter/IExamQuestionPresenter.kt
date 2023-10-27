package com.ma7moud27.exams.ui.examcreation.fragments.examquestion.presenter

import android.content.Context
import com.ma7moud27.exams.model.Question

interface IExamQuestionPresenter {
    fun updateViews(selectedType: Int)
    fun setFields(context: Context, questionIndex: Int)

    fun updateQuestionsList(context: Context, question: Question)
}
