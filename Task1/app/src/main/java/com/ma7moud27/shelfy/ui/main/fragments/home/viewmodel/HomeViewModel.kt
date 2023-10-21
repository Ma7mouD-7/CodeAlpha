package com.ma7moud27.shelfy.ui.main.fragments.home.viewmodel

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
import com.ma7moud27.shelfy.model.SearchBookResponse
import com.ma7moud27.shelfy.model.ServiceResponse
import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.ui.author.view.AuthorActivity
import com.ma7moud27.shelfy.ui.booksdisplay.view.BooksDisplayActivity
import com.ma7moud27.shelfy.ui.login.view.LoginActivity
import com.ma7moud27.shelfy.ui.main.fragments.home.repository.HomeRepository
import com.ma7moud27.shelfy.ui.work.view.WorkActivity
import com.ma7moud27.shelfy.utils.Constants
import com.ma7moud27.shelfy.utils.Constants.Companion.AUTHOR_KEY
import com.ma7moud27.shelfy.utils.Constants.Companion.AUTHOR_LIST
import com.ma7moud27.shelfy.utils.Constants.Companion.BOOK_KEY
import com.ma7moud27.shelfy.utils.Constants.Companion.CATEGORIES
import com.ma7moud27.shelfy.utils.Constants.Companion.CATEGORY_IDX
import com.ma7moud27.shelfy.utils.Constants.Companion.SENDER
import com.ma7moud27.shelfy.utils.Constants.Companion.WORK_KEY
import com.ma7moud27.shelfy.utils.UtilMethods.Companion.extractIdFromKey
import com.ma7moud27.shelfy.utils.enums.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _categoryListLiveData = MutableLiveData<ServiceResponse<List<Category>>>()
    val categoryListLiveData: LiveData<ServiceResponse<List<Category>>> = _categoryListLiveData

    private val _trendingListLiveData = MutableLiveData<ServiceResponse<SearchBookResponse>>()
    val trendingListLiveData: LiveData<ServiceResponse<SearchBookResponse>> = _trendingListLiveData

    private val _randomBookListLiveData = MutableLiveData<ServiceResponse<Work>>()
    val randomBookListLiveData: LiveData<ServiceResponse<Work>> = _randomBookListLiveData

    private val _authorsListLiveData = MutableLiveData<ServiceResponse<List<Author>>>()
    val authorsListLiveData: LiveData<ServiceResponse<List<Author>>> = _authorsListLiveData

    fun fetchCategoryList(noOfItems: Int) {
        _categoryListLiveData.value = ServiceResponse.Success(repository.getCategoryList(noOfItems))
    }

    fun fetchTrendingBooks(
        trendTime: Trending,
        page: Int = 1,
        limit: Int = 10,
    ) {
        _trendingListLiveData.value = ServiceResponse.Loading()
        viewModelScope.launch {
            _trendingListLiveData.value =
                ServiceResponse.Success(repository.getTrending(trendTime.query, page, limit))
        }
    }

    fun fetchRandomBook() {
        _randomBookListLiveData.value = ServiceResponse.Loading()
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
            val workID = searchBookItem?.key?.extractIdFromKey() ?: ""
            viewModelScope.launch {
                var work = repository.getWork(workID)
                if (!searchBookItem!!.lendingEditionKey.isNullOrEmpty()) {
                    work.book?.key = searchBookItem.lendingEditionKey
                } else if (!searchBookItem.coverEditionKey.isNullOrEmpty()) {
                    work.book?.key = searchBookItem.coverEditionKey
                }
                work.author = searchBookItem.authorName
                _randomBookListLiveData.value = ServiceResponse.Success(work)
            }
        }
    }

    fun fetchAuthors(noOfAuthors: Int) {
        _authorsListLiveData.value = ServiceResponse.Loading()
        viewModelScope.launch {
            _authorsListLiveData.value =
                ServiceResponse.Success(repository.getAuthors().shuffled().take(noOfAuthors))
        }
    }

    fun handelCategoryItemClick(context: Context, position: Int) {
        context.startActivity(
            Intent(context, BooksDisplayActivity::class.java).apply {
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
        val searchBookItem = _trendingListLiveData.value?.data?.items!![position]
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

    fun handelAuthorItemClick(context: Context, position: Int) {
        context.startActivity(
            Intent(context, AuthorActivity::class.java).apply {
                putExtra(
                    AUTHOR_KEY,
                    _authorsListLiveData.value?.data?.get(position)?.key?.substringAfterLast("/"),
                )
            },
        )
    }

    fun handelRandomBook(
        context: Context,
        titleTextView: TextView,
        coverImageView: ImageView,
    ) {

        var optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                Pair.create(titleTextView, "work_title_tn"),
                Pair.create(coverImageView, "work_cover_tn"),
            )

        context.startActivity(
            Intent(context, WorkActivity::class.java).apply {
                putExtra(WORK_KEY, _randomBookListLiveData.value?.data?.key?.extractIdFromKey())
                putExtra(
                    BOOK_KEY,
                    _randomBookListLiveData.value?.data?.book?.key?.extractIdFromKey(),
                )
                putExtra(
                    AUTHOR_LIST,
                    _randomBookListLiveData.value?.data?.author?.joinToString(", "),
                )
            },
            optionsCompat.toBundle(),
        )
    }

    fun logout(context: Context) {
        repository.logout()
        context.startActivity(
            Intent(context, LoginActivity::class.java),
        )
        (context as Activity).finish()
    }

    fun getName(): String? = repository.currentUser()?.displayName
}
