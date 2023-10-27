package com.ma7moud27.exams.ui.examcreation.fragments.examreview

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Exam
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.ui.adapter.OnQuestionsItemClickListener
import com.ma7moud27.exams.ui.adapter.QuestionsAdapter
import com.ma7moud27.exams.ui.examcreation.fragments.examreview.presenter.ExamReviewPresenter
import com.ma7moud27.exams.ui.examcreation.fragments.examreview.view.ExamReviewView
import com.ma7moud27.exams.ui.main.MainActivity

class ExamReviewFragment : Fragment(), ExamReviewView, OnQuestionsItemClickListener {
    private lateinit var codeTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var durationTextView: TextView
    private lateinit var finalGradeTextView: TextView
    private lateinit var questionsRecyclerView: RecyclerView
    private lateinit var adapter: QuestionsAdapter

    private lateinit var nextButton: Button
    private lateinit var cancelButton: Button

    private lateinit var presenter: ExamReviewPresenter
    private lateinit var exam: Exam

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.exam_creation_fragment_review, container, false)
        initComponents(view)

        nextButton.setOnClickListener {
            presenter.updateFirestoreExams(this.requireContext())
            activity?.apply {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@ExamReviewFragment.requireContext())
                .setTitle(getString(R.string.cancel_exam))
                .setMessage(getString(R.string.cancel_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    presenter.deleteExam(this.requireContext())
                    activity?.apply {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                .setNegativeButton(getString(R.string.no), null).show()
        }
        return view
    }

    private fun initComponents(view: View) {
        codeTextView = view.findViewById(R.id.creation_review_code_tv)
        titleTextView = view.findViewById(R.id.creation_review_title_tv)
        nameTextView = view.findViewById(R.id.creation_review_name_tv)
        durationTextView = view.findViewById(R.id.creation_review_duration_tv)
        finalGradeTextView = view.findViewById(R.id.creation_review_grade_tv)
        questionsRecyclerView = view.findViewById(R.id.creation_review_questions_rv)

        setupRecyclerView()

        presenter = ExamReviewPresenter(this)
        presenter.getExam(this.requireContext())
    }

    private fun setupRecyclerView() {
        adapter = QuestionsAdapter(listOf(), this)
        questionsRecyclerView.adapter = adapter
        questionsRecyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun setData(exam: Exam) {
        this.exam = exam
        codeTextView.text = exam.code
        titleTextView.text = exam.title
        nameTextView.text = exam.name
        durationTextView.text = exam.duration.toString()
        finalGradeTextView.text = exam.finalGrade.toString()
        adapter.setDataToAdapter(exam.questions)
    }

    override fun onQuestionsItemClick(position: Int) {
        Toast.makeText(
            this.requireContext(),
            exam.questions[position].let {
                when (it) {
                    is Question.MultipleChoice -> it.questionText
                    is Question.ShortAnswer -> it.questionText
                    is Question.Essay -> it.questionText
                }
            },
            Toast.LENGTH_SHORT,
        )
            .show()
    }
}
