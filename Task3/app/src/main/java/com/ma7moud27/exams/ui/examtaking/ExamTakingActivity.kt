package com.ma7moud27.exams.ui.examtaking

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.ui.examtaking.fragments.EssayQuestionFragment
import com.ma7moud27.exams.ui.examtaking.fragments.MultipleChoicesQuestionFragment
import com.ma7moud27.exams.ui.examtaking.fragments.ShortAnswerQuestionFragment
import com.ma7moud27.exams.ui.examtaking.presenter.ExamTakingPresenter
import com.ma7moud27.exams.ui.examtaking.view.ExamTakingView

class ExamTakingActivity : AppCompatActivity(), ExamTakingView {

    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: FragmentStateAdapter
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    private lateinit var questionsList: List<Question>

    private lateinit var presenter: ExamTakingPresenter
    private var currentQuestion = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_taking)
        initComponents()

        nextButton.setOnClickListener {
            setPagerItem(1)
        }
        prevButton.setOnClickListener { setPagerItem(-1) }
    }

    private fun initComponents() {
        nextButton = findViewById(R.id.exam_taking_next_btn)
        prevButton = findViewById(R.id.exam_taking_prev_btn)

        presenter = ExamTakingPresenter(this)
        viewPager = findViewById(R.id.exam_taking_view_pager)
        pagerAdapter = QuestionPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        questionsList = presenter.getQuestionsList()
        pagerAdapter.notifyDataSetChanged()

        setPagerItem(0)
        presenter.initResults(this)
    }

    private fun setPagerItem(i: Int) {
        currentQuestion += i
        viewPager.currentItem = currentQuestion
        prevButton.visibility = if (i == 0) View.GONE else View.VISIBLE
    }

    inner class QuestionPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStateAdapter(fragmentManager, this.lifecycle) {
        override fun getItemCount(): Int = questionsList.size

        override fun createFragment(position: Int): Fragment {
            return when (questionsList[position]) {
                is Question.MultipleChoice -> MultipleChoicesQuestionFragment().apply {
                    setQuestion(questionsList[position])
                }

                is Question.ShortAnswer -> ShortAnswerQuestionFragment().apply {
                    setQuestion(questionsList[position])
                }

                else -> EssayQuestionFragment().apply {
                    setQuestion(questionsList[position])
                }
            }
        }
    }
}
