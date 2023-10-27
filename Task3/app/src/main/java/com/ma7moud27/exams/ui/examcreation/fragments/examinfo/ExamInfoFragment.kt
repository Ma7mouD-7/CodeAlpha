package com.ma7moud27.exams.ui.examcreation.fragments.examinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.ma7moud27.exams.R
import com.ma7moud27.exams.ui.examcreation.ExamCreationActivity
import com.ma7moud27.exams.ui.examcreation.fragments.examinfo.presenter.ExamInfoPresenter
import com.ma7moud27.exams.ui.examcreation.fragments.examinfo.view.ExamInfoView
import com.ma7moud27.exams.utils.UtilMethods.toEditable

class ExamInfoFragment : Fragment(), ExamInfoView {
    private lateinit var codeEditText: EditText
    private lateinit var titleEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var durationEditText: EditText
    private lateinit var finalGradeEditText: EditText
    private lateinit var nextButton: Button

    private lateinit var presenter: ExamInfoPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.exam_creation_fragment_info, container, false)
        initComponents(view)

        nextButton.setOnClickListener {
            presenter.initExam(
                codeEditText.text.toString(),
                titleEditText.text.toString(),
                nameEditText.text.toString(),
                durationEditText.text.toString().toInt(),
                finalGradeEditText.text.toString().toInt(),
            )
            (activity as ExamCreationActivity).navigation(1)
        }
        return view
    }

    private fun initComponents(view: View) {
        codeEditText = view.findViewById(R.id.creation_exam_info_code_et)
        titleEditText = view.findViewById(R.id.creation_exam_info_title_et)
        nameEditText = view.findViewById(R.id.creation_exam_info_name_et)
        durationEditText = view.findViewById(R.id.creation_exam_info_duration_et)
        finalGradeEditText = view.findViewById(R.id.creation_exam_info_final_grade_et)
        nextButton = view.findViewById(R.id.creation_exam_info_next_btn)

        presenter = ExamInfoPresenter(this)
    }

    override fun clearFields() {
        codeEditText.text = "".toEditable()
        titleEditText.text = "".toEditable()
        nameEditText.text = "".toEditable()
        durationEditText.text = "".toEditable()
        finalGradeEditText.text = "".toEditable()
    }

    override fun setFields(
        code: String,
        title: String,
        name: String,
        duration: String,
        finalGrade: String,
    ) {
        codeEditText.text = code.toEditable()
        titleEditText.text = title.toEditable()
        nameEditText.text = name.toEditable()
        durationEditText.text = duration.toEditable()
        finalGradeEditText.text = finalGrade.toEditable()
    }

    override fun onPause() {
        super.onPause()
        presenter.initExam(
            codeEditText.text.toString(),
            titleEditText.text.toString(),
            nameEditText.text.toString(),
            durationEditText.text.toString().toInt(),
            finalGradeEditText.text.toString().toInt(),
        )
    }
}
