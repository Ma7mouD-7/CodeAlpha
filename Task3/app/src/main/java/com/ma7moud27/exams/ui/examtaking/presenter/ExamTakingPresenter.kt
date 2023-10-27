package com.ma7moud27.exams.ui.examtaking.presenter

import android.content.Context
import com.ma7moud27.exams.local.ExamDatabase
import com.ma7moud27.exams.local.ResultDatabase
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.model.Result
import com.ma7moud27.exams.network.firebase.FirebaseAuthClient
import com.ma7moud27.exams.ui.examtaking.view.ExamTakingView
import com.ma7moud27.exams.utils.Constants.Companion.DAO_EXAM_ID
import com.ma7moud27.exams.utils.Constants.Companion.DAO_RESULT_ID

class ExamTakingPresenter(private val view: ExamTakingView) : IExamTakingPresenter {
    override fun getQuestionsList(): List<Question> {
        return listOf(
            Question.MultipleChoice("Question", 10, listOf("1", "2", "3", "4")),
            Question.MultipleChoice("Question", 10, listOf("1", "2", "3", "4")),
            Question.MultipleChoice("Question", 10, listOf("1", "2", "3", "4")),
            Question.MultipleChoice("Question", 10, listOf("1", "2", "3", "4")),
            Question.MultipleChoice("Question", 10, listOf("1", "2", "3", "4")),
            Question.ShortAnswer("Question", 20, 50),
            Question.ShortAnswer("Question", 20, 50),
            Question.ShortAnswer("Question", 20, 50),
            Question.ShortAnswer("Question", 20, 50),
            Question.ShortAnswer("Question", 20, 50),
            Question.Essay("Question", 50, 100),
        )
    }

    override fun initResults(context: Context) {
        val exam = ExamDatabase.getInstance(context).ExamDao().getExam(DAO_EXAM_ID)
        ResultDatabase.getInstance(context).resultDao().insert(
            Result(
                DAO_RESULT_ID,
                exam.code,
                FirebaseAuthClient.currentUser()!!.uid,
                exam.creatorID,
                mutableListOf(),
                0.0,
            ),
        )
    }

    override fun saveAnswer(context: Context, idx: Int, answer: String) {
        val result = ResultDatabase.getInstance(context).resultDao().getAllResults()[0]
        result.answers[idx] = answer
    }
}
