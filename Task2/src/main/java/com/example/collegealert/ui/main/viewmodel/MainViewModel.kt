package com.example.collegealert.ui.main.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.collegealert.model.Task
import com.example.collegealert.ui.main.repository.MainRepository
import com.example.collegealert.ui.task.view.TaskActivity
import com.example.collegealert.utils.Constants.Companion.TASK_ID
import com.example.collegealert.utils.UtilMethods.compareDates
import com.example.collegealert.utils.enums.TaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val application: Application,
    private val repository: MainRepository,
) : AndroidViewModel(application) {

    private val _doneTasksLiveData = MutableLiveData<List<Task>>()
    val doneTasksLiveData: LiveData<List<Task>> = _doneTasksLiveData
    private val _passedTasksLiveData = MutableLiveData<List<Task>>()
    val passedTasksLiveData: LiveData<List<Task>> = _passedTasksLiveData
    private val _upcomingTasksLiveData = MutableLiveData<List<Task>>()
    val upcomingTasksLiveData: LiveData<List<Task>> = _upcomingTasksLiveData

    fun handleTaskItemClick(position: Int, taskType: TaskType) {
        application.applicationContext.startActivity(
            Intent(
                application.applicationContext,
                TaskActivity::class.java,
            ).apply {
                putExtra(
                    TASK_ID,
                    when (taskType) {
                        TaskType.DONE -> _doneTasksLiveData.value!![position].id
                        TaskType.PASSED -> _passedTasksLiveData.value!![position].id
                        TaskType.UPCOMING -> _upcomingTasksLiveData.value!![position].id
                    },
                )
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    fun fetchTasksFromDatabase() {
        viewModelScope.launch {
            val tasks = withContext(Dispatchers.IO) {
                repository.getTasks(application.applicationContext)
            }
            updateTasks(tasks)
            _doneTasksLiveData.value = tasks.filter { it.type == TaskType.DONE }
            _passedTasksLiveData.value = tasks.filter { it.type == TaskType.PASSED }
            _upcomingTasksLiveData.value = tasks.filter { it.type == TaskType.UPCOMING }
        }
    }

    private fun updateTasks(tasks: List<Task>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (task in tasks) {
                    if (task.type == TaskType.UPCOMING && compareDates(
                            task.deadlineDate,
                            task.deadlineEndTime,
                        )
                    ) {
                        task.type = TaskType.PASSED
                        repository.editTask(application.applicationContext, task)
                    }
                }
            }
        }
    }

    fun removeTask(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTask(
                    application.applicationContext,
                    id,
                )
            }
            fetchTasksFromDatabase()
        }
    }

    fun handleDone(id: Int) {
        viewModelScope.launch {
            val task = withContext(Dispatchers.IO) {
                repository.getTask(
                    application.applicationContext,
                    id,
                )
            }
            task.type = TaskType.DONE
            withContext(Dispatchers.IO) {
                repository.editTask(
                    application.applicationContext,
                    task,
                )
            }
            fetchTasksFromDatabase()
        }
    }
}
