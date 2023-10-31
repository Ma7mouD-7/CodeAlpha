package com.example.collegealert.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.collegealert.R
import com.example.collegealert.model.Task

class TaskAdapter(
    private val context: Context,
    private var taskList: List<Task>,
    private val listener: OnTaskItemClickListener,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false),
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onTaskItemClick(position) }
        val task = taskList[position]
        holder.titleTextView.text = task.title
        holder.dateTextView.text = "${ task.deadlineDate } ${ task.deadlineStartTime}"
        holder.locationTextView.text = task.location
        holder.descriptionTextView.text = task.description
        holder.priorityIcon.setBackgroundColor(
            ContextCompat.getColor(
                context,
                task.priority.colorId,
            ),
        )
        holder.taskType.apply {
            text = task.field.name.lowercase().replaceFirstChar { it.uppercase() }
            setTextColor(
                ContextCompat.getColor(
                    context,
                    task.field.colorId,
                ),
            )
        }
    }

    override fun getItemCount(): Int = taskList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView
        val dateTextView: TextView
        val locationTextView: TextView
        val descriptionTextView: TextView
        val priorityIcon: ImageView
        val taskType: TextView

        init {
            titleTextView = itemView.findViewById(R.id.item_task_title_tv)
            dateTextView = itemView.findViewById(R.id.item_task_date_tv)
            locationTextView = itemView.findViewById(R.id.item_task_location_tv)
            descriptionTextView = itemView.findViewById(R.id.item_task_desc_tv)
            priorityIcon = itemView.findViewById(R.id.item_task_priority_iv)
            taskType = itemView.findViewById(R.id.item_task_type_tv)
        }
    }
}

interface OnTaskItemClickListener {
    fun onTaskItemClick(position: Int)
}
