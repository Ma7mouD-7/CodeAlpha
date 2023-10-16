package com.ma7moud27.onlinebookshop.ui.main.fragments.home.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.SearchBookResponse
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.ui.author.view.AuthorActivity
import com.ma7moud27.onlinebookshop.ui.booksdisplay.view.BooksDisplayActivity
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.repository.HomeRepository
import com.ma7moud27.onlinebookshop.ui.profile.view.ProfileActivity
import com.ma7moud27.onlinebookshop.ui.work.view.WorkActivity
import com.ma7moud27.onlinebookshop.utils.Constants
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.AUTHOR_KEY
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.AUTHOR_LIST
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.BOOK_KEY
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.CATEGORIES
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.CATEGORY_IDX
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.SENDER
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.WORK_KEY
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.extractIdFromKey
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
        limit: Int = 10,
    ) {
        viewModelScope.launch {
            _trendingListLiveData.value = repository.getTrending(trendTime.query, page, limit)
        }
    }

    fun fetchRandomBook() {
        viewModelScope.launch {
            val searchBookItem = repository.searchBooks(
                (BookSearch.TITLE.query + "\"${('a'..'z').random()}\""),
                "ebooks",
                1,
                true,
                BookSort.TOP_RATED.query,
                Language.ENGLISH.query,
                50,
            ).items?.get((0..49).random())
            val workID = searchBookItem?.key?.extractIdFromKey()?: ""
            Log.d("MAHMOUD", "testRandomWorkID: $workID")
            viewModelScope.launch {
                var work = repository.getWork(workID)
                if(!searchBookItem!!.lendingEditionKey.isNullOrEmpty()) work.book?.key = searchBookItem.lendingEditionKey
                else if(!searchBookItem.coverEditionKey.isNullOrEmpty()) work.book?.key = searchBookItem.coverEditionKey
                work.author = searchBookItem.authorName
                _randomBookListLiveData.value = work
            }
        }
    }

    fun fetchAuthors(noOfAuthors: Int) {
        viewModelScope.launch {
            _authorsListLiveData.value = repository.getAuthors().shuffled().take(noOfAuthors)
        }
    }

    fun handelCategoryItemClick(context: Context, position: Int) {
        context.startActivity(
            Intent(context, BooksDisplayActivity::class.java).apply{
                putExtra(SENDER, CATEGORIES)
                putExtra(CATEGORY_IDX, position)
            },
        )
    }

    fun handelBookItemClick(
        context: Context,
        position: Int,
        titleTextView: TextView,
        coverImageView: ImageView,
    ) {
        val searchBookItem = _trendingListLiveData.value?.items!![position]
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

    fun handelAuthorItemClick(context: Context, position: Int) {
        context.startActivity(
            Intent(context, AuthorActivity::class.java).apply {
                putExtra(AUTHOR_KEY,
                    _authorsListLiveData.value?.get(position)?.key?.substringAfterLast("/")
                )
            }
        )
    }

    fun handelRandomBook(
        context: Context,
//        bannerImageView : ImageView,
        titleTextView : TextView,
//        authorTextView : TextView,
//        yearTextView : TextView,
        coverImageView : ImageView,
    ) {
        Log.d("MAHMOUD", "testRandomWorkmodel: ${_randomBookListLiveData.value?.key}")

        var optionsCompat : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            context as Activity,
            Pair.create(titleTextView, "work_title_tn"),
            Pair.create(coverImageView, "work_cover_tn")
        )
//            Pair.create(bannerImageView, "work_banner_tn"),
//            Pair.create(, "work_s_curve_tn"),
//            Pair.create(authorTextView, "work_author_tn"),
//            Pair.create(yearTextView, "work_year_tn"),

        context.startActivity(
            Intent(context, WorkActivity::class.java).apply {
                putExtra(WORK_KEY,_randomBookListLiveData.value?.key?.extractIdFromKey())
                putExtra(BOOK_KEY,_randomBookListLiveData.value?.book?.key?.extractIdFromKey())
                putExtra(AUTHOR_LIST,_randomBookListLiveData.value?.author?.joinToString(", "))
            },
            optionsCompat.toBundle()
        )
    }
}
