package com.example.collegealert.ui.main.repository

import android.content.Context
import com.example.collegealert.model.Task

interface MainRepository {
    suspend fun getTasks(context: Context): List<Task>
    suspend fun getTask(context: Context, id: Int): Task
    suspend fun editTask(context: Context, task: Task)
    suspend fun deleteTask(context: Context, id: Int)

}
