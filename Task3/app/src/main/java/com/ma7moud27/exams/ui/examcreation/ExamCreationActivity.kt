package com.ma7moud27.exams.ui.examcreation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ma7moud27.exams.R
import com.ma7moud27.exams.ui.examcreation.fragments.examinfo.ExamInfoFragment
import com.ma7moud27.exams.ui.examcreation.fragments.examquestion.ExamQuestionFragment
import com.ma7moud27.exams.ui.examcreation.fragments.examreview.ExamReviewFragment

class ExamCreationActivity : AppCompatActivity() {
    private lateinit var indicatorTextView: TextView

    private lateinit var examInfo: Fragment
    private lateinit var examQuestion: Fragment
    private lateinit var examReview: Fragment
    private lateinit var fragmentList: MutableList<Fragment>

    private var indicator: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_creation)
        initComponents()
        navigation(-1)
    }

    private fun initComponents() {
        indicatorTextView = findViewById(R.id.creation_indicator_tv)
        initFragments()
        fragmentList = mutableListOf()
    }

    private fun initFragments() {
        examInfo = ExamInfoFragment()
        examQuestion = ExamQuestionFragment()
        examReview = ExamReviewFragment()
    }

    fun navigation(ind: Int) {
        when (ind) {
            0 -> {
                indicatorTextView.text = getString(R.string.review)
                replaceFragment(examReview)
            }
            else -> {
                indicator += ind
                if (indicator == 0) {
                    indicatorTextView.text = ""
                    replaceFragment(examInfo)
                } else {
                    indicatorTextView.text = getString(R.string.question, indicator)
                    replaceFragment(ExamQuestionFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (indicator == fragmentList.size) fragmentList.add(fragment)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.creation_frame_layout, fragmentList[indicator])
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
    }
}
