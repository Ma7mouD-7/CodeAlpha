package com.example.collegealert.ui.main.repository

import android.content.Context
import com.example.collegealert.local.LocalDataSource
import com.example.collegealert.model.Task

class MainRepositoryImp(
    private var local: LocalDataSource,
) : MainRepository {
    override suspend fun getTasks(context: Context) = local.getAllTask(context)
    override suspend fun getTask(context: Context, id: Int): Task =
        local.getTask(context, id)

    override suspend fun editTask(context: Context, task: Task) {
        local.updateTask(context, task)
    }

    override suspend fun deleteTask(context: Context, id: Int) {
        local.deleteTask(context, id)
    }
}
