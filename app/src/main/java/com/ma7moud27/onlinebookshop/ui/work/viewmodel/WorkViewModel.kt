package com.ma7moud27.onlinebookshop.ui.work.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud27.onlinebookshop.model.work.Work
import com.ma7moud27.onlinebookshop.ui.work.repository.WorkRepo
import kotlinx.coroutines.launch

class WorkViewModel(private val repository: WorkRepo) : ViewModel() {
    private val _workListLiveData = MutableLiveData<Work>()
    val workListLiveData: LiveData<Work> = _workListLiveData

    fun fetchWork(workID: String, bookID: String) {
        viewModelScope.launch {
            val ratings = repository.getRatings(workID)
            Log.d("MAHMOUD", "WorkViewModel 19 Rating: $ratings")
            val book = repository.getBook(bookID)
            Log.d("MAHMOUD", "WorkViewModel 21 book: $book")

            viewModelScope.launch {
                val work = repository.getWork(workID)
                work.ratings = ratings
                work.book = book
                Log.d("MAHMOUD", "WorkViewModel 27 work: $work")
                _workListLiveData.value = work
            }
        }
    }
}
