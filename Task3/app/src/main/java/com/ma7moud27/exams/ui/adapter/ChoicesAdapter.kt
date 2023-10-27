package com.ma7moud27.exams.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.exams.R


class ChoicesAdapter(
    private var choicesList: List<String>,
    private val listener: OnChoicesItemClickListener
) : RecyclerView.Adapter<ChoicesAdapter.ChoicesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoicesViewHolder =
        ChoicesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_choices, parent, false)
        )


    override fun onBindViewHolder(holder: ChoicesViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onChoicesItemClick(position) }
    }

    override fun getItemCount(): Int = choicesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(choicesList: List<String>) {
        this.choicesList = choicesList
        notifyDataSetChanged()
    }

    class ChoicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

        }
    }
}

interface OnChoicesItemClickListener {
    fun onChoicesItemClick(position: Int)
}