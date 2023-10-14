package com.ma7moud27.onlinebookshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.repository.author.AuthorRepo
import kotlinx.coroutines.launch

class AuthorViewModel(private val repo: AuthorRepo) : ViewModel() {
    private val _authorLiveData = MutableLiveData<Author>()
    val authorLiveData: LiveData<Author> = _authorLiveData

    fun fetchAuthor(authorID: String){
        viewModelScope.launch {
            _authorLiveData.value = repo.getAuthor(authorID)
        }
    }


}