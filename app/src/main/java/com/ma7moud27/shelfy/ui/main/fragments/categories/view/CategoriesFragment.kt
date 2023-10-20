package com.ma7moud27.shelfy.ui.main.fragments.categories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.local.LocalDataClient
import com.ma7moud27.shelfy.network.openlibrary.OpenLibApiClient
import com.ma7moud27.shelfy.ui.adapter.CategoryListAdapter
import com.ma7moud27.shelfy.ui.adapter.OnCategoryListClickListener
import com.ma7moud27.shelfy.ui.main.fragments.categories.repository.CategoriesRepositoryImp
import com.ma7moud27.shelfy.ui.main.fragments.categories.viewmodel.CategoriesViewModel
import com.ma7moud27.shelfy.ui.main.fragments.categories.viewmodel.CategoriesViewModelFactory

class CategoriesFragment : Fragment(), OnCategoryListClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryListAdapter

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        prepareViewModel()
        setupRecycleView(view)

        viewModel.categoryListLiveData.observe(viewLifecycleOwner) { adapter.setDataToAdapter(it) }
        viewModel.fetchCategoryList()

        return view
    }

    private fun prepareViewModel() {
        val repository = CategoriesRepositoryImp(OpenLibApiClient, LocalDataClient)
        val viewModelFactory = CategoriesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CategoriesViewModel::class.java]
    }

    private fun setupRecycleView(view: View) {
        recyclerView = view.findViewById(R.id.categories_rv)
        adapter = CategoryListAdapter(listOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onCategoryListClick(position: Int) {
        viewModel.handelCategoryItemClick(this.requireContext(), position)
    }
}
