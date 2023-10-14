package com.ma7moud27.onlinebookshop.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.repository.search.SearchRepoImpl
import com.ma7moud27.onlinebookshop.utils.enums.BookSearch
import com.ma7moud27.onlinebookshop.viewmodel.SearchViewModel
import com.ma7moud27.onlinebookshop.viewmodel.factory.SearchViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var searchBtn: Button
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        prepareViewModel()
//        initComponents(view)
//
//        searchBtn.setOnClickListener {
//            searchViewModel.fetchBookSearch(BookSearch.TITLE.query + "\"${editText.text}\"")
//        }
//
//        searchViewModel.searchBookLiveData.observe(viewLifecycleOwner){
//            textView.text = buildString{
//                append("Number of items: ${it.numFound}\n\n")
//
//                it.items.forEach {
//                    append("Title: ${it.title}\n\n")
//                }
//            }
//        }
        return view
    }

    private fun prepareViewModel() {
        val searchRepo = SearchRepoImpl(OpenLibApiClient)
        val searchViewModelFactory = SearchViewModelFactory(searchRepo)
        searchViewModel = ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]
    }

//    private fun initComponents(view: View) {
//
//    }
}
