package com.ma7moud27.shelfy.ui.main.fragments.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.model.ServiceResponse
import com.ma7moud27.shelfy.network.openlibrary.OpenLibApiClient
import com.ma7moud27.shelfy.ui.adapter.BookAdapter
import com.ma7moud27.shelfy.ui.adapter.OnBookItemClickListener
import com.ma7moud27.shelfy.ui.main.fragments.search.repository.SearchRepoImpl
import com.ma7moud27.shelfy.ui.main.fragments.search.viewmodel.SearchViewModel
import com.ma7moud27.shelfy.ui.main.fragments.search.viewmodel.SearchViewModelFactory
import com.ma7moud27.shelfy.utils.enums.BookSearch

class SearchFragment : Fragment(), OnBookItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var search: SearchView
    private lateinit var typesGroup: RadioGroup

    private lateinit var shimmerLayout: ShimmerFrameLayout

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        prepareViewModel()
        initComponents(view)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //            viewModel.fetchBookSearch(BookSearch.TITLE.query + "\"${search.query}\"")
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val type = when (typesGroup.checkedRadioButtonId) {
                    R.id.search_title_radio_btn -> BookSearch.TITLE.query
                    R.id.search_author_radio_btn -> BookSearch.AUTHOR.query
                    else -> ""
                }
//                Toast.makeText(
//                    this@SearchFragment.requireContext(),
//                    "Search submit " + type + "\"${search.query}\"",
//                    Toast.LENGTH_SHORT,
//                ).show()
                viewModel.fetchBookSearch(type + "\"${search.query}\"")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0.isNullOrEmpty()) adapter.setDataToAdapter(listOf())
                return false
            }
        })

        viewModel.searchBookLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ServiceResponse.Loading -> {
                    shimmerLayout.apply {
                        startShimmer()
                        visibility = View.VISIBLE
                    }
                }
                is ServiceResponse.Success -> {
                    it.data?.items?.let { it1 -> adapter.setDataToAdapter(it1) }
                    shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                }
                else -> {
                    shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                }
            }
        }
        return view
    }

    private fun prepareViewModel() {
        val repository = SearchRepoImpl(OpenLibApiClient)
        val viewModelFactory = SearchViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
    }

    private fun initComponents(view: View) {
        recyclerView = view.findViewById(R.id.search_rv)
        setupRecyclerView()

        search = view.findViewById(R.id.searchView)
        typesGroup = view.findViewById(R.id.search_type_group)
        shimmerLayout = view.findViewById(R.id.search_shimmer_layout)
    }

    private fun setupRecyclerView() {
        adapter =
            BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_horizontal)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
    }

    override fun onBookItemClick(holder: BookAdapter.BookViewHolder, position: Int) {
        viewModel.handelBookItemClick(
            this.requireContext(),
            position,
            holder.titleTextView,
            holder.coverImageView,
        )
    }
}
