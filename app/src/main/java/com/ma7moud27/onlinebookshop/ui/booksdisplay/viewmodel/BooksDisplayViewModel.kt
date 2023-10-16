package com.ma7moud27.onlinebookshop.ui.booksdisplay.viewmodel

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
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.ui.booksdisplay.repository.BooksDisplayRepository
import com.ma7moud27.onlinebookshop.ui.work.view.WorkActivity
import com.ma7moud27.onlinebookshop.utils.Constants
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.extractIdFromKey
import com.ma7moud27.onlinebookshop.utils.enums.BookSearch
import com.ma7moud27.onlinebookshop.utils.enums.BookSort
import com.ma7moud27.onlinebookshop.utils.enums.Category
import com.ma7moud27.onlinebookshop.utils.enums.Trending
import kotlinx.coroutines.launch

class BooksDisplayViewModel(private val repository: BooksDisplayRepository) : ViewModel() {
    private val _booksItemsLiveData = MutableLiveData<SearchBookResponse>()
    val booksItemsLiveData: LiveData<SearchBookResponse> = _booksItemsLiveData

    fun fetchTrendingBooks(
        trendTime: Trending,
        page: Int = 1,
        limit: Int = 50,
    ) {
        viewModelScope.launch {
            _booksItemsLiveData.value = repository.getTrending(trendTime.query, page, limit)
        }
    }

    fun fetchCategory(
        position: Int,
        mode: String = "ebooks",
        page: Int = 1,
        isFullText: Boolean = true,
        sort: String = BookSort.TOP_RATED.query,
        language: String = "",
        limit: Int = 100,
    ) {
        val category = repository.getCategoryList(Category.values().size)[position].query
        viewModelScope.launch {
            _booksItemsLiveData.value = repository.searchBooks(BookSearch.SUBJECT.query + "\"$category\"", mode, page, isFullText, sort, language, limit)
        }
    }

    fun fetchBookSearch(
        authorName: String,
        mode: String = "ebooks",
        page: Int = 1,
        isFullText: Boolean = true,
        sort: String = BookSort.RELEVANCE.query,
        language: String = "",
        limit: Int = 100,
    ) {
        viewModelScope.launch {
            _booksItemsLiveData.value = repository.searchBooks(BookSearch.AUTHOR.query + "\"$authorName\"", mode, page, isFullText, sort, language, limit)
        }
    }

    fun handelBookItemClick(
        context: Context,
        position: Int,
        titleTextView: TextView,
        coverImageView: ImageView,
    ) {
        val searchBookItem = _booksItemsLiveData.value?.items!![position]
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

    fun getCategory(categoryIndex: Int): String =
        repository.getCategoryList(Category.values().size)[categoryIndex].name.lowercase().replaceFirstChar { it.uppercase() }
}
