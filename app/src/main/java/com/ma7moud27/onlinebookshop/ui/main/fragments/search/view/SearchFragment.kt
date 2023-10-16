package com.ma7moud27.onlinebookshop.ui.main.fragments.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.ui.adapter.BookAdapter
import com.ma7moud27.onlinebookshop.ui.adapter.OnBookItemClickListener
import com.ma7moud27.onlinebookshop.ui.main.fragments.search.repository.SearchRepoImpl
import com.ma7moud27.onlinebookshop.ui.main.fragments.search.viewmodel.SearchViewModel
import com.ma7moud27.onlinebookshop.ui.main.fragments.search.viewmodel.SearchViewModelFactory
import com.ma7moud27.onlinebookshop.utils.enums.BookSearch

class SearchFragment : Fragment(), OnBookItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var search: SearchView
    private lateinit var typesGroup: RadioGroup

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
                Toast.makeText(
                    this@SearchFragment.requireContext(),
                    "Search submit " + type + "\"${search.query}\"",
                    Toast.LENGTH_SHORT,
                ).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Toast.makeText(
                    this@SearchFragment.requireContext(),
                    "Search \"${search.query}\"",
                    Toast.LENGTH_SHORT,
                ).show()
                return false
            }
        })

        viewModel.searchBookLiveData.observe(viewLifecycleOwner) {
            it.items?.let { it1 -> adapter.setDataToAdapter(it1) }
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
