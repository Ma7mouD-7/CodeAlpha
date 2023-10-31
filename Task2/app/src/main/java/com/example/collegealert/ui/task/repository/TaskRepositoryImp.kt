package com.example.collegealert.ui.task.repository

import android.content.Context
import com.example.collegealert.local.LocalDataSource
import com.example.collegealert.model.Task

class TaskRepositoryImp(
    private val localDataSource: LocalDataSource,
) : TaskRepository {
    override suspend fun getTask(context: Context, id: Int) = localDataSource.getTask(context, id)
    override suspend fun addTask(context: Context, task: Task) = localDataSource.insertTask(context, task)

    override suspend fun editTask(context: Context, task: Task) = localDataSource.updateTask(context, task)
}
