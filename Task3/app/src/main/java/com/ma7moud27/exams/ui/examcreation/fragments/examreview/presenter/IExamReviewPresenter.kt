package com.ma7moud27.exams.ui.examcreation.fragments.examreview.presenter

import android.content.Context

interface IExamReviewPresenter {
    fun getExam(context: Context)
    fun deleteExam(context: Context)
    fun updateFirestoreExams(context: Context)
}
