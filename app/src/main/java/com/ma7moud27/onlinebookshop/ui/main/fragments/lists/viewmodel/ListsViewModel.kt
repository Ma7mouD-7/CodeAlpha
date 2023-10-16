package com.ma7moud27.onlinebookshop.ui.main.fragments.lists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.ui.main.fragments.lists.repository.ListsRepository

class ListsViewModel(private val repository: ListsRepository) : ViewModel() {
    private val _favouriteLiveData = MutableLiveData<SearchBookResponse>()
    val favouriteLiveData: LiveData<SearchBookResponse> = _favouriteLiveData
    private val _currentLiveData = MutableLiveData<SearchBookResponse>()
    val currentLiveData: LiveData<SearchBookResponse> = _currentLiveData
    private val _planLiveData = MutableLiveData<SearchBookResponse>()
    val planLiveData: LiveData<SearchBookResponse> = _planLiveData
    private val _doneLiveData = MutableLiveData<SearchBookResponse>()
    val doneLiveData: LiveData<SearchBookResponse> = _doneLiveData

    fun fetchLists() {
        fetchFavoriteList()
        fetchCurrentList()
        fetchPlanList()
        fetchDoneList()
    }

    private fun fetchFavoriteList() {
        TODO("Not yet implemented")
    }

    private fun fetchCurrentList() {
        TODO("Not yet implemented")
    }

    private fun fetchPlanList() {
        TODO("Not yet implemented")
    }

    private fun fetchDoneList() {
        TODO("Not yet implemented")
    }

}