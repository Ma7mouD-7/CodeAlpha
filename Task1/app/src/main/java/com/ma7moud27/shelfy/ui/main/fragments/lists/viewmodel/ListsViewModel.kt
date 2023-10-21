package com.ma7moud27.shelfy.ui.main.fragments.lists.viewmodel

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
import com.google.firebase.firestore.ktx.toObject
import com.ma7moud27.shelfy.model.SearchBookItem
import com.ma7moud27.shelfy.model.ServiceResponse
import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.ui.main.fragments.lists.repository.ListsRepository
import com.ma7moud27.shelfy.ui.work.view.WorkActivity
import com.ma7moud27.shelfy.utils.Constants
import com.ma7moud27.shelfy.utils.UtilMethods.Companion.extractIdFromKey
import kotlinx.coroutines.launch

class ListsViewModel(private val repository: ListsRepository) : ViewModel() {
    private val _favouriteLiveData = MutableLiveData<ServiceResponse<List<SearchBookItem>>>()
    val favouriteLiveData: LiveData<ServiceResponse<List<SearchBookItem>>> = _favouriteLiveData
    private val _currentLiveData = MutableLiveData<ServiceResponse<List<SearchBookItem>>>()
    val currentLiveData: LiveData<ServiceResponse<List<SearchBookItem>>> = _currentLiveData
    private val _planLiveData = MutableLiveData<ServiceResponse<List<SearchBookItem>>>()
    val planLiveData: LiveData<ServiceResponse<List<SearchBookItem>>> = _planLiveData
    private val _doneLiveData = MutableLiveData<ServiceResponse<List<SearchBookItem>>>()
    val doneLiveData: LiveData<ServiceResponse<List<SearchBookItem>>> = _doneLiveData
    private val listBooks = MutableLiveData<MutableList<SearchBookItem>>()

    fun fetchLists() {
        _favouriteLiveData.value = ServiceResponse.Loading()
        _currentLiveData.value = ServiceResponse.Loading()
        _planLiveData.value = ServiceResponse.Loading()
        _doneLiveData.value = ServiceResponse.Loading()
        val currentUser = repository.getCurrentUser()
        viewModelScope.launch {
            repository.getUser(currentUser!!.uid).addOnSuccessListener {
                val user = it.toObject<User>()
                if (user != null) {
                    _favouriteLiveData.value = if (user.favoriteList.isEmpty()) {
                        ServiceResponse.Error(message = "")
                    } else {
                        ServiceResponse.Success(
                            user.favoriteList,
                        )
                    }
                    _currentLiveData.value = if (user.currentlyReadList.isEmpty()) {
                        ServiceResponse.Error(message = "")
                    } else {
                        ServiceResponse.Success(
                            user.currentlyReadList,
                        )
                    }
                    _planLiveData.value = if (user.planToReadList.isEmpty()) {
                        ServiceResponse.Error(message = "")
                    } else {
                        ServiceResponse.Success(
                            user.planToReadList,
                        )
                    }
                    _doneLiveData.value = if (user.doneReadingList.isEmpty()) {
                        ServiceResponse.Error(message = "")
                    } else {
                        ServiceResponse.Success(
                            user.doneReadingList,
                        )
                    }
                }
                makeCollection()
            }
        }
    }

    private fun makeCollection() {
        val items = mutableListOf<SearchBookItem>()
        items.addAll(_favouriteLiveData.value?.data ?: listOf())
        items.addAll(_currentLiveData.value?.data ?: listOf())
        items.addAll(_planLiveData.value?.data ?: listOf())
        items.addAll(_doneLiveData.value?.data ?: listOf())
        listBooks.value = items
    }

    fun handelBookItemClick(
        context: Context,
        position: Int,
        titleTextView: TextView,
        coverImageView: ImageView,
    ) {
        val searchBookItem = listBooks.value?.get(position)
        val workID = searchBookItem!!.key?.extractIdFromKey() ?: ""
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
