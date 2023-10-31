package com.example.collegealert.local

import android.content.Context
import com.example.collegealert.local.task.TaskDatabase
import com.example.collegealert.model.Task

/*
* Created by Ma7mouD on Mon 30/10/2023 at 02:23 AM.
*/
object DatabaseClient : LocalDataSource {
    override suspend fun insertTask(context: Context, task: Task) =
        TaskDatabase.getInstance(context).taskDao().insert(task)

    override suspend fun updateTask(context: Context, task: Task) =
        TaskDatabase.getInstance(context).taskDao().update(task)

    override suspend fun deleteTask(context: Context, task: Task) =
        TaskDatabase.getInstance(context).taskDao().delete(task)

    override suspend fun deleteTask(context: Context, id: Int) {
        TaskDatabase.getInstance(context).taskDao().delete(id)
    }

    override suspend fun deleteAllTask(context: Context) =
        TaskDatabase.getInstance(context).taskDao().deleteAll()

    override suspend fun getAllTask(context: Context): List<Task> =
        TaskDatabase.getInstance(context).taskDao().getAll()

    override suspend fun getTask(context: Context, id: Int): Task =
        TaskDatabase.getInstance(context).taskDao().get(id)
}
