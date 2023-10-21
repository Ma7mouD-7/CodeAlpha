package com.ma7moud27.shelfy.ui.main.fragments.categories.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ma7moud27.shelfy.ui.booksdisplay.view.BooksDisplayActivity
import com.ma7moud27.shelfy.ui.main.fragments.categories.repository.CategoriesRepository
import com.ma7moud27.shelfy.utils.Constants
import com.ma7moud27.shelfy.utils.enums.Category

class CategoriesViewModel(private val repository: CategoriesRepository) : ViewModel() {
    private val _categoryListLiveData = MutableLiveData<List<Category>>()
    val categoryListLiveData: LiveData<List<Category>> = _categoryListLiveData

    fun fetchCategoryList() {
        _categoryListLiveData.value = repository.getCategoryList(Category.values().size)
    }

    fun handelCategoryItemClick(context: Context, position: Int) {
        context.startActivity(
            Intent(context, BooksDisplayActivity::class.java).apply {
                putExtra(Constants.SENDER, Constants.CATEGORIES)
                putExtra(Constants.CATEGORY_IDX, position)
            },
        )
    }
}
