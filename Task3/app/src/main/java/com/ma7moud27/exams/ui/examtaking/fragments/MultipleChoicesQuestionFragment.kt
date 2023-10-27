package com.ma7moud27.exams.ui.examtaking.fragments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.ui.adapter.ChoicesAdapter
import com.ma7moud27.exams.ui.adapter.OnChoicesItemClickListener
import com.ma7moud27.exams.ui.examtaking.ExamTakingActivity
import com.ma7moud27.exams.ui.examtaking.presenter.ExamTakingPresenter

class MultipleChoicesQuestionFragment : QuestionFragment(), OnChoicesItemClickListener {
    private lateinit var questionText: TextView
    private lateinit var questionPoints: TextView
    private lateinit var choicesRecycler: RecyclerView
    private lateinit var choicesAdapter: ChoicesAdapter

    private lateinit var presenter: ExamTakingPresenter
    private var question: Question.MultipleChoice? = null
    private var index: Int = 0
    private var answer: String = ""
    override fun customizeUi(view: View) {
        initComponents(view)

        questionText.text = question!!.questionText
        questionPoints.text = question!!.questionPoints.toString()
        choicesAdapter.setDataToAdapter(question!!.choices)
    }

    private fun initComponents(view: View) {
        questionText = view.findViewById(R.id.taking_exam_mcq_text_tv)
        questionPoints = view.findViewById(R.id.taking_exam_mcq_points_tv)
        choicesRecycler = view.findViewById(R.id.taking_exam_mcq_rv)
        setupChoicesRecyclerView()

        presenter = ExamTakingPresenter(activity as ExamTakingActivity)
    }

    private fun setupChoicesRecyclerView() {
        choicesAdapter = ChoicesAdapter(listOf(), this)
        choicesRecycler.adapter = choicesAdapter
        choicesRecycler.layoutManager = GridLayoutManager(this.requireContext(), 2)
    }

    override fun getLayoutId(): Int = R.layout.exam_taking_fragment_multiple_choices
    override fun setQuestion(question: Question) {
        this.question = question as Question.MultipleChoice
    }

    override fun setIndex(index: Int) {
        this.index = index
    }

    override fun onPause() {
        super.onPause()
        presenter.saveAnswer(activity as ExamTakingActivity, index - 1, answer)
    }

    override fun onChoicesItemClick(position: Int) {
        answer = question!!.choices[position]
    }
}
