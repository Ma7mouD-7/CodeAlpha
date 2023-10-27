package com.ma7moud27.exams.ui.examtaking.presenter

import android.content.Context
import com.ma7moud27.exams.model.Question

interface IExamTakingPresenter {
    fun getQuestionsList(): List<Question>
    fun initResults(context: Context)
    fun saveAnswer(context: Context, idx: Int, answer: String)
}
