package com.ma7moud27.onlinebookshop.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.onlinebookshop.repository.work.WorkRepo
import com.ma7moud27.onlinebookshop.viewmodel.WorkViewModel

class WorkViewModelFactory(private val workRepo: WorkRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkViewModel::class.java)) {
            return WorkViewModel(workRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}