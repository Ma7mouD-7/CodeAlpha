package com.ma7moud27.exams.ui.examtaking.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ma7moud27.exams.model.Question

abstract class QuestionFragment : Fragment() {
    abstract fun customizeUi(view: View)
    abstract fun getLayoutId(): Int
    abstract fun setQuestion(question: Question)
    abstract fun setIndex(index: Int)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        customizeUi(view)
        return view
    }
}
