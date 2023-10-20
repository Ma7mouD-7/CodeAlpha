package com.ma7moud27.shelfy.ui.work.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.toObject
import com.ma7moud27.shelfy.model.SearchBookItem
import com.ma7moud27.shelfy.model.User
import com.ma7moud27.shelfy.model.work.Work
import com.ma7moud27.shelfy.ui.work.repository.WorkRepo
import com.ma7moud27.shelfy.utils.Constants
import com.ma7moud27.shelfy.utils.UtilMethods.Companion.extractIdFromKey
import kotlinx.coroutines.launch

class WorkViewModel(private val repository: WorkRepo) : ViewModel() {
    private val _workListLiveData = MutableLiveData<Work>()
    val workListLiveData: LiveData<Work> = _workListLiveData
    private val _isFavouriteListLiveData = MutableLiveData<Boolean>()
    val isFavouriteListLiveData: LiveData<Boolean> = _isFavouriteListLiveData

    fun fetchWork(workID: String, bookID: String, authorList: String) {
        viewModelScope.launch {
            val ratings = repository.getRatings(workID)
            Log.d("MAHMOUD", "WorkViewModel 19 Rating: $ratings")
            val book = repository.getBook(bookID)
            Log.d("MAHMOUD", "WorkViewModel 21 book: $book")

            viewModelScope.launch {
                val work = repository.getWork(workID)
                work.ratings = ratings
                work.book = book
                work.author = listOf(authorList)
                Log.d("MAHMOUD", "WorkViewModel 27 work: $work")
                _workListLiveData.value = work
            }
        }
        fetchIsFav(workID)
    }

    private fun fetchIsFav(workID: String = "") {
        val currentUser = repository.getCurrentUser()
        Log.d("FETCH_LISTS", "fav icon:")
        viewModelScope.launch {
            Log.d("FETCH_LISTS", "fav icon:launch")

            repository.getUser(currentUser!!.uid).addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                Log.d("FETCH_LISTS", "fav icon: $user")
                val contains =
                    user?.favoriteList?.map { it.key?.extractIdFromKey() ?: "" }?.contains(workID)
                Log.d("FETCH_LISTS", "fav icon contains: $contains")
                _isFavouriteListLiveData.value = contains!!
            }
        }
    }

    fun handleLists(list: String) {
        val currentUser = repository.getCurrentUser()
        viewModelScope.launch {
            repository.getUser(currentUser!!.uid).addOnSuccessListener {
                val user = it.toObject<User>()
                val cWork = workListLiveData.value!!
                Log.d("FETCH_LISTS", "handleLists: Work $cWork")
                val inList = isInList(user!!, cWork, list)
                Log.d("FETCH_LISTS", "handleLists: count $inList")
                when (inList) {
                    true -> addToList(currentUser, user, cWork, list)
                    false -> removeFromList(currentUser, user, cWork, list)
                }
                fetchIsFav(cWork.key!!.extractIdFromKey())
            }
        }
    }

    private fun isInList(user: User, cWork: Work, list: String): Boolean = when (list) {
        Constants.PLAN -> user.planToReadList.count { it.key == cWork.key } == 0
        Constants.CURRENT -> user.currentlyReadList.count { it.key == cWork.key } == 0
        Constants.DONE -> user.doneReadingList.count { it.key == cWork.key } == 0
        Constants.FAVOURITE -> user.favoriteList.count { it.key == cWork.key } == 0
        else -> false
    }

    private fun addToList(
        currentUser: FirebaseUser,
        user: User,
        cWork: Work,
        list: String,
    ) {
        when (list) {
            Constants.PLAN -> user.planToReadList.add(
                SearchBookItem(
                    key = cWork.key,
                    title = cWork.title,
                    authorName = cWork.author,
                    coverID = cWork.covers?.first(),
                ),
            )
            Constants.CURRENT -> user.currentlyReadList.add(
                SearchBookItem(
                    key = cWork.key,
                    title = cWork.title,
                    authorName = cWork.author,
                    coverID = cWork.covers?.first(),
                ),
            )
            Constants.DONE -> user.doneReadingList.add(
                SearchBookItem(
                    key = cWork.key,
                    title = cWork.title,
                    authorName = cWork.author,
                    coverID = cWork.covers?.first(),
                ),
            )
            Constants.FAVOURITE -> user.favoriteList.add(
                SearchBookItem(
                    key = cWork.key,
                    title = cWork.title,
                    authorName = cWork.author,
                    coverID = cWork.covers?.first(),
                ),
            )
        }
        viewModelScope.launch {
            repository.updateUser(currentUser.uid, user)
        }
//        Toast.makeText(activity, "Added to $list", Toast.LENGTH_SHORT).show()
    }

    private fun removeFromList(
        currentUser: FirebaseUser,
        user: User,
        cWork: Work,
        list: String,
    ) {
        viewModelScope.launch {
            when (list) {
                Constants.PLAN -> user.planToReadList.removeIf { work -> work.key == cWork.key }
                Constants.CURRENT -> user.currentlyReadList.removeIf { work -> work.key == cWork.key }
                Constants.DONE -> user.doneReadingList.removeIf { work -> work.key == cWork.key }
                Constants.FAVOURITE -> user.favoriteList.removeIf { work -> work.key == cWork.key }
            }
            viewModelScope.launch {
                repository.updateUser(currentUser.uid, user)
            }
        }
//        Toast.makeText(activity, "Removed from $list", Toast.LENGTH_SHORT).show()
    }
}
