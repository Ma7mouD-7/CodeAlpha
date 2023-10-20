package com.ma7moud27.shelfy.ui.main.fragments.lists.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.local.LocalDataClient
import com.ma7moud27.shelfy.model.SearchBookItem
import com.ma7moud27.shelfy.model.ServiceResponse
import com.ma7moud27.shelfy.network.openlibrary.OpenLibApiClient
import com.ma7moud27.shelfy.ui.adapter.BookAdapter
import com.ma7moud27.shelfy.ui.adapter.OnBookItemClickListener
import com.ma7moud27.shelfy.ui.main.fragments.lists.repository.ListsRepositoryImp
import com.ma7moud27.shelfy.ui.main.fragments.lists.viewmodel.ListsViewModel
import com.ma7moud27.shelfy.ui.main.fragments.lists.viewmodel.ListsViewModelFactory

class ListsFragment : Fragment(), OnBookItemClickListener {
    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var currentlyRecyclerView: RecyclerView
    private lateinit var planRecyclerView: RecyclerView
    private lateinit var doneRecyclerView: RecyclerView
    private lateinit var favouritesEmpty: LinearLayout
    private lateinit var currentlyEmpty: LinearLayout
    private lateinit var planEmpty: LinearLayout
    private lateinit var doneEmpty: LinearLayout
    private lateinit var favouritesShimmer: ShimmerFrameLayout
    private lateinit var currentlyShimmer: ShimmerFrameLayout
    private lateinit var planShimmer: ShimmerFrameLayout
    private lateinit var doneShimmer: ShimmerFrameLayout
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
        viewModel.favouriteLiveData.observe(viewLifecycleOwner) { observeData(it, favouritesRecyclerView, favouritesEmpty, favouritesNumTextView, favouritesShimmer, favouritesAdapter) }
        viewModel.currentLiveData.observe(viewLifecycleOwner) { observeData(it, currentlyRecyclerView, currentlyEmpty, currentlyNumTextView, currentlyShimmer, currentlyAdapter) }
        viewModel.planLiveData.observe(viewLifecycleOwner) { observeData(it, planRecyclerView, planEmpty, planNumTextView, planShimmer, planAdapter) }
        viewModel.doneLiveData.observe(viewLifecycleOwner) { observeData(it, doneRecyclerView, doneEmpty, doneNumTextView, doneShimmer, doneAdapter) }
        viewModel.fetchLists()

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
        favouritesShimmer = view.findViewById(R.id.lists_fav_shimmer_layout)
        setupFavouritesRecyclerView()
        currentlyRecyclerView = view.findViewById(R.id.lists_cur_rv)
        currentlyEmpty = view.findViewById(R.id.lists_cur_empty)
        currentlyNumTextView = view.findViewById(R.id.lists_cur_num_tv)
        currentlyShimmer = view.findViewById(R.id.lists_cur_shimmer_layout)
        setupCurrentRecyclerView()
        planRecyclerView = view.findViewById(R.id.lists_plan_rv)
        planEmpty = view.findViewById(R.id.lists_plan_empty)
        planNumTextView = view.findViewById(R.id.lists_plan_num_tv)
        planShimmer = view.findViewById(R.id.lists_plan_shimmer_layout)
        setupPlanRecyclerView()
        doneRecyclerView = view.findViewById(R.id.lists_done_rv)
        doneEmpty = view.findViewById(R.id.lists_done_empty)
        doneNumTextView = view.findViewById(R.id.lists_done_num_tv)
        doneShimmer = view.findViewById(R.id.lists_done_shimmer_layout)
        setupDoneRecyclerView()
    }

    private fun setupFavouritesRecyclerView() {
        favouritesAdapter =
            BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
        favouritesRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        favouritesRecyclerView.adapter = favouritesAdapter
    }

    private fun setupCurrentRecyclerView() {
        currentlyAdapter =
            BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
        currentlyRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        currentlyRecyclerView.adapter = currentlyAdapter
    }

    private fun setupPlanRecyclerView() {
        planAdapter = BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
        planRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        planRecyclerView.adapter = planAdapter
    }

    private fun setupDoneRecyclerView() {
        doneAdapter = BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_small)
        doneRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        doneRecyclerView.adapter = doneAdapter
    }

    private fun observeData(
        items: ServiceResponse<List<SearchBookItem>>,
        recyclerView: RecyclerView,
        empty: LinearLayout,
        numTextView: TextView,
        shimmer: ShimmerFrameLayout,
        adapter: BookAdapter,
    ) {
        when (items) {
            is ServiceResponse.Loading -> {
                shimmer.startShimmer()
                shimmer.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                empty.visibility = View.GONE
                numTextView.visibility = View.GONE
            }
            is ServiceResponse.Success -> {
                shimmer.stopShimmer()
                shimmer.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                numTextView.visibility = View.VISIBLE
                empty.visibility = View.GONE
                adapter.setDataToAdapter(items.data!!)
                "${items.data.size} Book(s)".also { numTextView.text = it }
            }
            is ServiceResponse.Error -> {
                shimmer.stopShimmer()
                shimmer.visibility = View.INVISIBLE
                recyclerView.visibility = View.GONE
                numTextView.visibility = View.GONE
                empty.visibility = View.VISIBLE
            }
        }
    }

    override fun onBookItemClick(holder: BookAdapter.BookViewHolder, position: Int) {
        viewModel.handelBookItemClick(this.requireContext(), position, holder.titleTextView, holder.coverImageView)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLists()
    }
}
