package com.ma7moud27.onlinebookshop.ui.work.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.ui.work.repository.WorkRepo
import kotlinx.coroutines.launch

class WorkViewModel(private val repo: WorkRepo) : ViewModel() {
    private val _workListLiveData = MutableLiveData<List<Work>>()
    val workListLiveData: LiveData<List<Work>> = _workListLiveData

    fun fetchWork(vararg workIDs: String) {
        viewModelScope.launch {
            val works = workIDs.map { id ->
                repo.getWork(id)
            }
            _workListLiveData.value = works
        }
    }
}