package com.ma7moud27.onlinebookshop.ui.main.fragments.home.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.ui.author.AuthorActivity
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.repository.HomeRepository
import com.ma7moud27.onlinebookshop.ui.work.WorkActivity
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.AUTHOR_KEY
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.BOOK_KEY
import com.ma7moud27.onlinebookshop.utils.enums.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _categoryListLiveData = MutableLiveData<List<Category>>()
    val categoryListLiveData: LiveData<List<Category>> = _categoryListLiveData

    private val _trendingListLiveData = MutableLiveData<SearchBookResponse>()
    val trendingListLiveData: LiveData<SearchBookResponse> = _trendingListLiveData

    private val _randomBookListLiveData = MutableLiveData<Work>()
    val randomBookListLiveData: LiveData<Work> = _randomBookListLiveData

    private val _authorsListLiveData = MutableLiveData<List<Author>>()
    val authorsListLiveData: LiveData<List<Author>> = _authorsListLiveData

    fun fetchCategoryList(noOfItems: Int) {
        _categoryListLiveData.value = repository.getCategoryList(noOfItems)
    }

    fun fetchTrendingBooks(
        trendTime: Trending,
        page: Int = 1,
        limit: Int = 1,
    ) {
        viewModelScope.launch {
            _trendingListLiveData.value = repository.getTrending(trendTime.query, page, limit)
        }
    }

    fun fetchRandomBook() {
        viewModelScope.launch {
            val workID = repository.searchBooks(
                (BookSearch.TITLE.query + "\"${('a'..'z').random()}\""),
                "ebooks",
                1,
                true,
                BookSort.TOP_RATED.query,
                Language.ALL.query,
                5,
            ).items[(0..4).random()].key.substringAfterLast("/")
            viewModelScope.launch {
                _randomBookListLiveData.value = repository.getWork(workID)
            }
        }
    }

    fun fetchAuthors(noOfAuthors: Int) {
        viewModelScope.launch {
            _authorsListLiveData.value = repository.getAuthors().shuffled().take(noOfAuthors)
        }
    }

    fun handelCategoryItemClick(context: Context, position: Int) {
        Toast.makeText(
            context,
            categoryListLiveData.value?.get(position)?.query ?: "clicked",
            Toast.LENGTH_SHORT,
        ).show()
    }

    fun handelBookItemClick(context: Context, position: Int) {
        Toast.makeText(
            context,
            trendingListLiveData.value?.items?.get(position)?.title ?: "clicked",
            Toast.LENGTH_SHORT,
        ).show()
    }

    fun handelAuthorItemClick(context: Context, position: Int) {
        context.startActivity(
            Intent(context,AuthorActivity::class.java).apply {
                putExtra(AUTHOR_KEY,
                    _authorsListLiveData.value?.get(position)?.key?.substringAfterLast("/")
                )
            }
        )
    }

    fun handelRandomBook(context: Context) {
        context.startActivity(
            Intent(context, WorkActivity::class.java).apply {
                putExtra(BOOK_KEY,"OL82563W")
            }
        )
    }
}
