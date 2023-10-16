package com.ma7moud27.onlinebookshop.ui.booksdisplay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.onlinebookshop.ui.booksdisplay.repository.BooksDisplayRepository

class BooksDisplayViewModelFactory(private val repository: BooksDisplayRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksDisplayViewModel::class.java)) {
            return BooksDisplayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}