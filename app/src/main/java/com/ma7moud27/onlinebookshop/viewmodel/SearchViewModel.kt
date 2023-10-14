package com.ma7moud27.onlinebookshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.SearchAuthorResponse
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.repository.search.SearchRepo
import com.ma7moud27.onlinebookshop.utils.enums.*
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: SearchRepo) : ViewModel() {
    private val _searchBookLiveData = MutableLiveData<SearchBookResponse>()
    val searchBookLiveData: LiveData<SearchBookResponse> = _searchBookLiveData
    private val _searchAuthorLiveData = MutableLiveData<SearchAuthorResponse>()
    val searchAuthorLiveData: LiveData<SearchAuthorResponse> = _searchAuthorLiveData

    fun fetchBookSearch(query: String,
                        mode: String = "ebooks",
                        page: Int = 1,
                        isFullText: Boolean = true,
                        sort: String = BookSort.RELEVANCE.query,
                        language: String = "",
                        limit: Int){
        viewModelScope.launch {
            _searchBookLiveData.value = repo.searchBooks(query, mode, page, isFullText, sort, language,limit)
        }
    }



    fun fetchAuthorSearch(
        query: String,
        page: Int = 1,
        sort: String = BookSort.RELEVANCE.query
    ){
        viewModelScope.launch {
            _searchAuthorLiveData.value = repo.searchAuthors(query, page, sort)
        }
    }


}