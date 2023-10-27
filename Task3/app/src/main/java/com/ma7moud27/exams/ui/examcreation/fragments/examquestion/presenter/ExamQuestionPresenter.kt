package com.ma7moud27.exams.ui.examcreation.fragments.examquestion.presenter

import android.content.Context
import androidx.fragment.app.Fragment
import com.ma7moud27.exams.R
import com.ma7moud27.exams.local.ExamDatabase
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.ui.examcreation.fragments.examquestion.view.ExamQuestionView
import com.ma7moud27.exams.utils.Constants.Companion.DAO_EXAM_ID
import java.util.concurrent.Executors

class ExamQuestionPresenter(private val view: ExamQuestionView) : IExamQuestionPresenter {
    override fun updateViews(selectedType: Int) {
        when (selectedType) {
            R.id.creation_question_mcq_radio_btn ->
                view.updateViews(
                    R.string.choices,
                    R.string.choices_hint,
                    4,
                    300,
                )
            R.id.creation_question_short_radio_btn ->
                view.updateViews(
                    R.string.max_chars_count,
                    R.string.max_chars_count,
                    1,
                    2,
                )
            R.id.creation_question_essay_radio_btn ->
                view.updateViews(
                    R.string.max_words_count,
                    R.string.max_words_count,
                    5,
                    1000,
                )
        }
    }

    override fun setFields(context: Context, questionIndex: Int) {
//            when (question) {
//                is MultipleChoice -> view.setFields(
//                    question.questionText,
//                    question.points.toString(),
//                    question.choices.joinToString("\n"),
//                    context.getString(R.string.choices),
//                    R.id.creation_question_mcq_radio_btn,
//                )
//
//                is ShortAnswer -> view.setFields(
//                    question.questionText,
//                    question.points.toString(),
//                    question.maxCharsCount.toString(),
//                    context.getString(R.string.max_chars_count),
//                    R.id.creation_question_mcq_radio_btn,
//                )
//
//                is Essay -> view.setFields(
//                    question.questionText,
//                    question.points.toString(),
//                    question.maxWordsCount.toString(),
//                    context.getString(R.string.max_words_count),
//                    R.id.creation_question_mcq_radio_btn,
//                )
//            }
    }

    override fun updateQuestionsList(context: Context, question: Question) {
        val examDao = ExamDatabase.getInstance((view as Fragment).requireContext()).ExamDao()
        Executors.newSingleThreadExecutor().execute {
            val exam = examDao.getExam(DAO_EXAM_ID)
            exam.questions.add(question)
            examDao.update(exam)
        }
    }
}
