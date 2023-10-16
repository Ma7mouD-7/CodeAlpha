package com.ma7moud27.onlinebookshop.ui.booksdisplay.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.local.LocalDataClient
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.ui.adapter.BookAdapter
import com.ma7moud27.onlinebookshop.ui.adapter.OnBookItemClickListener
import com.ma7moud27.onlinebookshop.ui.booksdisplay.repository.BooksDisplayRepositoryImp
import com.ma7moud27.onlinebookshop.ui.booksdisplay.viewmodel.BooksDisplayViewModel
import com.ma7moud27.onlinebookshop.ui.booksdisplay.viewmodel.BooksDisplayViewModelFactory
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.AUTHOR
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.AUTHOR_NAME
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.CATEGORIES
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.CATEGORY_IDX
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.SENDER
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.TRENDING
import com.ma7moud27.onlinebookshop.utils.enums.Trending

class BooksDisplayActivity : AppCompatActivity(), OnBookItemClickListener {

    private lateinit var itemsAdapter: BookAdapter
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var titleTextView: TextView

    private lateinit var viewModel: BooksDisplayViewModel
    private lateinit var sender: String
    private lateinit var authorName: String
    private var categoryIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_display)

        prepareViewModel()
        initComponents()

        when (sender) {
            TRENDING -> {
                viewModel.fetchTrendingBooks(Trending.TODAY)
                titleTextView.text = "Trending"
            }
            CATEGORIES -> {
                viewModel.fetchCategory(categoryIndex)
                titleTextView.text = viewModel.getCategory(categoryIndex)
            }
            AUTHOR -> {
                viewModel.fetchBookSearch(authorName)
                "$authorName's Books".also { titleTextView.text = it }
            }
            else -> Log.d("TAG", "onCreate a")
        }

        viewModel.booksItemsLiveData.observe(this) {
            if (it.items.isNullOrEmpty()) {
                Log.d("TAG", "onCreate a")
            } else {
                itemsAdapter.setDataToAdapter(it.items)
            }
        }
    }

    private fun prepareViewModel() {
        val repository = BooksDisplayRepositoryImp(OpenLibApiClient, LocalDataClient)
        val viewModelFactory = BooksDisplayViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[BooksDisplayViewModel::class.java]
    }

    private fun initComponents() {
        titleTextView = findViewById(R.id.display_title_tv)
        itemsRecyclerView = findViewById(R.id.display_rv)
        setupRecyclerView()

        intent.apply {
            sender = getStringExtra(SENDER) ?: ""
            categoryIndex = getIntExtra(CATEGORY_IDX, 0)
            authorName = getStringExtra(AUTHOR_NAME) ?: ""
        }
    }
    private fun setupRecyclerView() {
        itemsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        itemsAdapter = BookAdapter(listOf(), this, this, R.layout.item_book_horizontal)
        itemsRecyclerView.adapter = itemsAdapter
    }

    override fun onBookItemClick(holder: BookAdapter.BookViewHolder, position: Int) {
        viewModel.handelBookItemClick(this, position, holder.titleTextView, holder.coverImageView)
    }
}
