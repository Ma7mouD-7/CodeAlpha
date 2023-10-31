package com.example.collegealert.ui.task.repository

import android.content.Context
import com.example.collegealert.model.Task

interface TaskRepository {
    suspend fun getTask(context: Context, id: Int): Task
    suspend fun addTask(context: Context, task: Task)
    suspend fun editTask(context: Context, task: Task)
}
