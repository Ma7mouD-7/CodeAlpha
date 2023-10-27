package com.ma7moud27.exams.ui.examcreation.fragments.examquestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Question
import com.ma7moud27.exams.ui.examcreation.ExamCreationActivity
import com.ma7moud27.exams.ui.examcreation.fragments.examquestion.presenter.ExamQuestionPresenter
import com.ma7moud27.exams.ui.examcreation.fragments.examquestion.view.ExamQuestionView
import com.ma7moud27.exams.utils.UtilMethods.toEditable
import com.ma7moud27.exams.utils.UtilMethods.toInt
import com.ma7moud27.exams.utils.UtilMethods.toListChoices

class ExamQuestionFragment : Fragment(), ExamQuestionView {
    private lateinit var questionEditText: EditText
    private lateinit var pointsEditText: EditText
    private lateinit var typesRadioGroup: RadioGroup

    private lateinit var questionInfoTextView: TextView
    private lateinit var questionInfoEditText: EditText

    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var endButton: Button

    private lateinit var presenter: ExamQuestionPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.exam_creation_fragment_question, container, false)
        initComponents(view)

        nextButton.setOnClickListener {
            (activity as ExamCreationActivity).navigation(1)
            presenter.updateQuestionsList(this.requireContext(), createQuestionObject())
        }
        prevButton.setOnClickListener {
            (activity as ExamCreationActivity).navigation(-1)
        }
        endButton.setOnClickListener {
            (activity as ExamCreationActivity).navigation(0)
            presenter.updateQuestionsList(this.requireContext(), createQuestionObject())
        }
        typesRadioGroup.setOnCheckedChangeListener { _, i ->
            presenter.updateViews(i)
        }
        return view
    }

    private fun initComponents(view: View) {
        questionEditText = view.findViewById(R.id.creation_question_question_et)
        pointsEditText = view.findViewById(R.id.creation_question_points_et)
        typesRadioGroup = view.findViewById(R.id.creation_question_types_group)
        questionInfoTextView = view.findViewById(R.id.creation_question_info_tv)
        questionInfoEditText = view.findViewById(R.id.creation_question_info_et)

        nextButton = view.findViewById(R.id.creation_question_next_btn)
        prevButton = view.findViewById(R.id.creation_question_prev_btn)
        endButton = view.findViewById(R.id.creation_question_end_btn)

        presenter = ExamQuestionPresenter(this)
        typesRadioGroup.check(R.id.creation_question_mcq_radio_btn)
    }

    override fun createQuestionObject(): Question =
        when (typesRadioGroup.checkedRadioButtonId) {
            R.id.creation_question_mcq_radio_btn -> Question.MultipleChoice(
                questionEditText.text.toString(),
                pointsEditText.text.toInt(),
                questionInfoEditText.text.toListChoices(),
            )

            R.id.creation_question_short_radio_btn -> Question.ShortAnswer(
                questionEditText.text.toString(),
                pointsEditText.text.toInt(),
                questionInfoEditText.text.toInt(),
            )

            else -> Question.Essay(
                questionEditText.text.toString(),
                pointsEditText.text.toInt(),
                questionInfoEditText.text.toInt(),
            )
        }

    override fun clearFields() {
        questionEditText.text = "".toEditable()
        pointsEditText.text = "".toEditable()
        questionInfoEditText.text = "".toEditable()
        questionInfoTextView.text = getString(R.string.choices)
        typesRadioGroup.check(R.id.creation_question_mcq_radio_btn)
    }

    override fun setFields(
        question: String,
        points: String,
        option: String,
        optionText: String,
        type: Int,
    ) {
        questionEditText.text = question.toEditable()
        pointsEditText.text = points.toEditable()
        questionInfoEditText.text = option.toEditable()
        questionInfoTextView.text = optionText
        typesRadioGroup.check(type)
    }

    override fun updateViews(placeholder: Int, hint: Int, minLines: Int, maxEms: Int) {
        questionInfoTextView.text = getString(placeholder)
        questionInfoEditText.hint = getString(hint)
        questionInfoEditText.minLines = minLines
        questionInfoEditText.maxEms = maxEms
    }
}
