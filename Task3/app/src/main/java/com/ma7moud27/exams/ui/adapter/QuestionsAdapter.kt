package com.ma7moud27.exams.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.exams.R
import com.ma7moud27.exams.model.Question

class QuestionsAdapter(
    private var question: List<Question>,
    private val listener: OnQuestionsItemClickListener,
) : RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder =
        QuestionsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false),
        )

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onQuestionsItemClick(position) }
    }

    override fun getItemCount(): Int = question.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(question: List<Question>) {
        this.question = question
        notifyDataSetChanged()
    }

    class QuestionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }
    }
}

interface OnQuestionsItemClickListener {
    fun onQuestionsItemClick(position: Int)
}
