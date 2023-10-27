package com.ma7moud27.exams.ui.examcreation.fragments.examreview.presenter

import android.content.Context
import android.widget.Toast
import com.ma7moud27.exams.R
import com.ma7moud27.exams.local.ExamDatabase
import com.ma7moud27.exams.network.firebase.FirebaseFirestoreClient
import com.ma7moud27.exams.ui.examcreation.fragments.examreview.view.ExamReviewView
import com.ma7moud27.exams.utils.Constants.Companion.DAO_EXAM_ID
import java.util.concurrent.Executors

class ExamReviewPresenter(private val view: ExamReviewView) : IExamReviewPresenter {
    override fun getExam(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            view.setData(
                ExamDatabase.getInstance(context).ExamDao().getExam(DAO_EXAM_ID),
            )
        }
    }

    override fun deleteExam(context: Context) {
        val examDao = ExamDatabase.getInstance(context).ExamDao()
        Executors.newSingleThreadExecutor().execute {
            val exam = examDao.getExam(DAO_EXAM_ID)
            examDao.delete(exam)
        }
    }

    override fun updateFirestoreExams(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            FirebaseFirestoreClient.addExam(
                ExamDatabase.getInstance(context).ExamDao().getExam(DAO_EXAM_ID),
            ).addOnSuccessListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.exam_added_successfully),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}
