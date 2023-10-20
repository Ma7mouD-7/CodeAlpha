package com.ma7moud27.shelfy.ui.main.fragments.search.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.shelfy.model.SearchAuthorResponse
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.ServiceResponse
import com.ma7moud27.shelfy.ui.main.fragments.search.repository.SearchRepo
import com.ma7moud27.shelfy.ui.work.view.WorkActivity
import com.ma7moud27.shelfy.utils.Constants
import com.ma7moud27.shelfy.utils.UtilMethods.Companion.extractIdFromKey
import com.ma7moud27.shelfy.utils.enums.BookSort
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepo) : ViewModel() {
    private val _searchBookLiveData = MutableLiveData<ServiceResponse<SearchBookResponse>>()
    val searchBookLiveData: LiveData<ServiceResponse<SearchBookResponse>> = _searchBookLiveData
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
        _searchBookLiveData.value = ServiceResponse.Loading()
        viewModelScope.launch {
            _searchBookLiveData.value = ServiceResponse.Success(
                repository.searchBooks(
                    query,
                    mode,
                    page,
                    isFullText,
                    sort,
                    language,
                    limit,
                ),
            )
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
        val searchBookItem = _searchBookLiveData.value?.data?.items!![position]
        val workID = searchBookItem.key?.extractIdFromKey() ?: ""
        viewModelScope.launch {
            val work = repository.getWork(workID)
            if (!searchBookItem.lendingEditionKey.isNullOrEmpty()) {
                work.book?.key = searchBookItem.lendingEditionKey
            } else if (!searchBookItem.coverEditionKey.isNullOrEmpty()) {
                work.book?.key = searchBookItem.coverEditionKey
            }
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
