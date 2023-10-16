package com.ma7moud27.onlinebookshop.ui.main.fragments.search.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.SearchAuthorResponse
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.ui.main.fragments.search.repository.SearchRepo
import com.ma7moud27.onlinebookshop.ui.work.view.WorkActivity
import com.ma7moud27.onlinebookshop.utils.Constants
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.extractIdFromKey
import com.ma7moud27.onlinebookshop.utils.enums.BookSort
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepo) : ViewModel() {
    private val _searchBookLiveData = MutableLiveData<SearchBookResponse>()
    val searchBookLiveData: LiveData<SearchBookResponse> = _searchBookLiveData
    private val _searchAuthorLiveData = MutableLiveData<SearchAuthorResponse>()
    val searchAuthorLiveData: LiveData<SearchAuthorResponse> = _searchAuthorLiveData

    fun fetchBookSearch(
        query: String,
        mode: String = "ebooks",
        page: Int = 1,
        isFullText: Boolean = true,
        sort: String = BookSort.RELEVANCE.query,
        language: String = "",
        limit: Int = 100,
    ) {
        viewModelScope.launch {
            _searchBookLiveData.value =
                repository.searchBooks(query, mode, page, isFullText, sort, language, limit)
        }
    }

    fun fetchAuthorSearch(
        query: String,
        page: Int = 1,
        sort: String = BookSort.RELEVANCE.query,
    ) {
        viewModelScope.launch {
            _searchAuthorLiveData.value = repository.searchAuthors(query, page, sort)
        }
    }

    fun handelBookItemClick(
        context: Context,
        position: Int,
        titleTextView: TextView,
        coverImageView: ImageView,
    ) {
        val searchBookItem = _searchBookLiveData.value?.items!![position]
        val workID = searchBookItem.key?.extractIdFromKey() ?: ""
        Log.d("MAHMOUD", "testRandomWorkID: $workID")
        viewModelScope.launch {
            val work = repository.getWork(workID)
            if (!searchBookItem.lendingEditionKey.isNullOrEmpty()) {
                work.book?.key = searchBookItem.lendingEditionKey
            } else if (!searchBookItem.coverEditionKey.isNullOrEmpty()) work.book?.key = searchBookItem.coverEditionKey
            work.author = searchBookItem.authorName
            context.startActivity(
                Intent(context, WorkActivity::class.java).apply {
                    putExtra(Constants.WORK_KEY, workID)
                    putExtra(Constants.BOOK_KEY, work.book?.key?.extractIdFromKey())
                    putExtra(Constants.AUTHOR_LIST, work.author?.joinToString(", "))
                },
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    Pair.create(titleTextView, "work_title_tn"),
                    Pair.create(coverImageView, "work_cover_tn"),
                ).toBundle(),
            )
        }
    }
}
