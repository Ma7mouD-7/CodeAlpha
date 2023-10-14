package com.ma7moud27.onlinebookshop.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.onlinebookshop.repository.search.SearchRepo
import com.ma7moud27.onlinebookshop.viewmodel.SearchViewModel

class SearchViewModelFactory(private val searchRepo: SearchRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}