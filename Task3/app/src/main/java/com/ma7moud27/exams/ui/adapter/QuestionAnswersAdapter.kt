package com.ma7moud27.exams.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Exam
import com.ma7moud27.exams.model.Result

class QuestionAnswersAdapter(
    private var questionAnswers: Pair<Exam, Result>,
    private val listener: OnQuestionAnswersItemClickListener,
) : RecyclerView.Adapter<QuestionAnswersAdapter.QuestionAnswersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAnswersViewHolder =
        QuestionAnswersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_question_answer, parent, false),
        )

    override fun onBindViewHolder(holder: QuestionAnswersViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onQuestionAnswersItemClick(position) }
    }

    override fun getItemCount(): Int = questionAnswers.first.questions.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(questionAnswers: Pair<Exam, Result>) {
        this.questionAnswers = questionAnswers
        notifyDataSetChanged()
    }

    class QuestionAnswersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }
    }
}

interface OnQuestionAnswersItemClickListener {
    fun onQuestionAnswersItemClick(position: Int)
}
