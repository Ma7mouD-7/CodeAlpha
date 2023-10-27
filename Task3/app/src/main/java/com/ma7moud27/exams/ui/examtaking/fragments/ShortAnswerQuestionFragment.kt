package com.ma7moud27.exams.ui.examtaking.fragments

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.ui.examtaking.ExamTakingActivity
import com.ma7moud27.exams.ui.examtaking.presenter.ExamTakingPresenter

class ShortAnswerQuestionFragment : QuestionFragment() {
    private lateinit var questionText: TextView
    private lateinit var questionPoints: TextView
    private lateinit var questionAnswer: EditText

    private lateinit var presenter: ExamTakingPresenter
    private var question: Question.ShortAnswer? = null
    private var index: Int = 0
    override fun customizeUi(view: View) {
        initComponents(view)

        questionText.text = question!!.questionText
        questionPoints.text = question!!.questionPoints.toString()
        questionAnswer.maxEms = question!!.maxCharsCount
    }

    private fun initComponents(view: View) {
        questionText = view.findViewById(R.id.taking_exam_short_text_tv)
        questionPoints = view.findViewById(R.id.taking_exam_short_points_tv)
        questionAnswer = view.findViewById(R.id.taking_exam_short_answer_et)

        presenter = ExamTakingPresenter(activity as ExamTakingActivity)
    }

    override fun getLayoutId(): Int = R.layout.exam_taking_fragment_short_answer
    override fun setQuestion(question: Question) {
        this.question = question as Question.ShortAnswer
    }

    override fun setIndex(index: Int) {
        this.index = index
    }

    override fun onPause() {
        super.onPause()
        presenter.saveAnswer(activity as ExamTakingActivity, index - 1, questionAnswer.text.toString())
    }
}
