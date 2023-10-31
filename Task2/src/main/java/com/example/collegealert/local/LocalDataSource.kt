package com.example.collegealert.local

import android.content.Context
import com.example.collegealert.model.Task

interface LocalDataSource {
    suspend fun insertTask(context: Context, task: Task)
    suspend fun updateTask(context: Context, task: Task)
    suspend fun deleteTask(context: Context, task: Task)
    suspend fun deleteTask(context: Context, id: Int)
    suspend fun deleteAllTask(context: Context)
    suspend fun getAllTask(context: Context): List<Task>
    suspend fun getTask(context: Context, id: Int): Task
}
