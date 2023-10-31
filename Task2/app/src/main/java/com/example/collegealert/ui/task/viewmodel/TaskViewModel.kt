package com.example.collegealert.ui.task.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.collegealert.model.Task
import com.example.collegealert.ui.task.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(
    private val application: Application,
    private val repository: TaskRepository,
) : AndroidViewModel(application) {
    private val _taskLiveData = MutableLiveData<Task>()
    val taskLiveData: LiveData<Task> = _taskLiveData
    fun fetchTask(taskId: Int) {
        viewModelScope.launch {
            _taskLiveData.value = when (taskId) {
                -1 -> Task()
                else -> withContext(Dispatchers.IO) {
                    repository.getTask(application.applicationContext, taskId)
                }
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addTask(application.applicationContext, task)
            }
        }
    }

    fun editTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.editTask(application.applicationContext, task)
            }
        }
    }
}
