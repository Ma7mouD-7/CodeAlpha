package com.ma7moud27.onlinebookshop.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.onlinebookshop.repository.author.AuthorRepo
import com.ma7moud27.onlinebookshop.viewmodel.AuthorViewModel

class AuthorViewModelFactory(private val authorRepo: AuthorRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorViewModel::class.java)) {
            return AuthorViewModel(authorRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}