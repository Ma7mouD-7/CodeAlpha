package com.ma7moud27.exams.ui.examcreation.fragments.examinfo.presenter

import androidx.fragment.app.Fragment
import com.ma7moud27.exams.local.ExamDatabase
import com.ma7moud27.exams.local.UserDatabase
import com.ma7moud27.exams.model.Exam
import com.ma7moud27.exams.ui.examcreation.fragments.examinfo.view.ExamInfoView
import java.util.concurrent.Executors

class ExamInfoPresenter(val view: ExamInfoView) : IExamInfoPresenter {
    private val db = ExamDatabase.getInstance((view as Fragment).requireContext()).ExamDao()
    override fun initExam(
        code: String,
        name: String,
        title: String,
        duration: Int,
        finalGrade: Int,
    ) {
        Executors.newSingleThreadExecutor().execute {
            db.insert(
                Exam(
                    1,
                    code,
                    UserDatabase.getInstance((view as Fragment).requireContext()).userDao().getAllCreators()[0].id,
                    name,
                    title,
                    duration,
                    finalGrade,
                    mutableListOf(),
                ),
            )
        }
    }
}
