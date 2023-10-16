package com.ma7moud27.onlinebookshop.ui.main.fragments.lists.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.local.LocalDataClient
import com.ma7moud27.onlinebookshop.model.SearchBookItem
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.ui.adapter.BookAdapter
import com.ma7moud27.onlinebookshop.ui.adapter.OnBookItemClickListener
import com.ma7moud27.onlinebookshop.ui.main.fragments.lists.repository.ListsRepositoryImp
import com.ma7moud27.onlinebookshop.ui.main.fragments.lists.viewmodel.ListsViewModel
import com.ma7moud27.onlinebookshop.ui.main.fragments.lists.viewmodel.ListsViewModelFactory

class ListsFragment : Fragment(), OnBookItemClickListener {
    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var currentlyRecyclerView: RecyclerView
    private lateinit var planRecyclerView: RecyclerView
    private lateinit var doneRecyclerView: RecyclerView
    private lateinit var favouritesEmpty: LinearLayout
    private lateinit var currentlyEmpty: LinearLayout
    private lateinit var planEmpty: LinearLayout
    private lateinit var doneEmpty: LinearLayout
    private lateinit var favouritesNumTextView: TextView
    private lateinit var currentlyNumTextView: TextView
    private lateinit var planNumTextView: TextView
    private lateinit var doneNumTextView: TextView
    private lateinit var favouritesAdapter: BookAdapter
    private lateinit var currentlyAdapter: BookAdapter
    private lateinit var planAdapter: BookAdapter
    private lateinit var doneAdapter: BookAdapter

    private lateinit var viewModel: ListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lists, container, false)
        prepareViewModel()
        initComponents(view)
        viewModel.favouriteLiveData.observe(viewLifecycleOwner) { observeData(it.items, favouritesRecyclerView, favouritesEmpty, favouritesNumTextView, favouritesAdapter) }
        viewModel.currentLiveData.observe(viewLifecycleOwner) { observeData(it.items, currentlyRecyclerView, currentlyEmpty, currentlyNumTextView, currentlyAdapter) }
        viewModel.planLiveData.observe(viewLifecycleOwner) { observeData(it.items, planRecyclerView, planEmpty, planNumTextView, planAdapter) }
        viewModel.doneLiveData.observe(viewLifecycleOwner) { observeData(it.items, doneRecyclerView, doneEmpty, doneNumTextView, doneAdapter) }
//        viewModel.fetchLists()

        return view
    }

    private fun prepareViewModel() {
        val repository = ListsRepositoryImp(OpenLibApiClient, LocalDataClient)
        val viewModelFactory = ListsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListsViewModel::class.java]
    }

    private fun initComponents(view: View) {
        favouritesRecyclerView = view.findViewById(R.id.lists_fav_rv)
        favouritesEmpty = view.findViewById(R.id.lists_fav_empty)
        favouritesNumTextView = view.findViewById(R.id.lists_fav_num_tv)
        setupFavouritesRecyclerView()
        currentlyRecyclerView = view.findViewById(R.id.lists_cur_rv)
        currentlyEmpty = view.findViewById(R.id.lists_cur_empty)
        currentlyNumTextView = view.findViewById(R.id.lists_cur_num_tv)
        setupCurrentRecyclerView()
        planRecyclerView = view.findViewById(R.id.lists_plan_rv)
        planEmpty = view.findViewById(R.id.lists_plan_empty)
        planNumTextView = view.findViewById(R.id.lists_plan_num_tv)
        setupPlanRecyclerView()
        doneRecyclerView = view.findViewById(R.id.lists_done_rv)
        doneEmpty = view.findViewById(R.id.lists_done_empty)
        doneNumTextView = view.findViewById(R.id.lists_done_num_tv)
        setupDoneRecyclerView()
    }

    private fun setupFavouritesRecyclerView() {
        favouritesAdapter =
            BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
    }

    private fun setupCurrentRecyclerView() {
        currentlyAdapter =
            BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
    }

    private fun setupPlanRecyclerView() {
        planAdapter = BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
    }

    private fun setupDoneRecyclerView() {
        doneAdapter = BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
    }

    private fun observeData(
        items: List<SearchBookItem>?,
        recyclerView: RecyclerView,
        empty: LinearLayout,
        numTextView: TextView,
        adapter: BookAdapter,
    ) {
        if (items.isNullOrEmpty()) {
            recyclerView.visibility = View.GONE
            numTextView.visibility = View.GONE
            empty.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            numTextView.visibility = View.VISIBLE
            empty.visibility = View.GONE
            adapter.setDataToAdapter(items)
            "${items.size} Book(s)".also { numTextView.text = it }
        }
    }

    override fun onBookItemClick(holder: BookAdapter.BookViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
