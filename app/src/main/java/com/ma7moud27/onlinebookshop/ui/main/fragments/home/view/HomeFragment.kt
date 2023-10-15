package com.ma7moud27.onlinebookshop.ui.main.fragments.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.local.LocalDataClient
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.ui.main.MainActivity
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.adapter.* // ktlint-disable no-wildcard-imports
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.repository.HomeRepositoryImp
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.viewmodel.HomeViewModel
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.viewmodel.HomeViewModelFactory
import com.ma7moud27.onlinebookshop.utils.UtilMethods
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.clearSources
import com.ma7moud27.onlinebookshop.utils.enums.CoverKey
import com.ma7moud27.onlinebookshop.utils.enums.CoverSize

class HomeFragment :
    Fragment(),
    OnCategoryItemClickListener,
    OnBookItemClickListener,
    OnAuthorItemClickListener {

    private lateinit var profileButton: ImageButton
    private lateinit var trendButton: Button
    private lateinit var categoryButton: Button
    private lateinit var randomButton: Button
    private lateinit var randomTitleTextView: TextView
    private lateinit var randomDescriptionTextView: TextView
    private lateinit var randomCoverImageView: ImageView

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var trendRecyclerView: RecyclerView
    private lateinit var trendAdapter: BookAdapter
    private lateinit var authorRecyclerView: RecyclerView
    private lateinit var authorAdapter: AuthorAdapter

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        prepareViewModel()
        initComponents(view)
        fetchData()
        categoryButton.setOnClickListener {
            (activity as MainActivity?)!!.replaceFragment(
                (activity as MainActivity?)!!.categories,
                R.id.main_menu_categories,
            )
        }
        randomButton.setOnClickListener {
            homeViewModel.handelRandomBook(
                this.requireContext(),
                randomTitleTextView,
                randomCoverImageView
            )
        }
        return view
    }

    private fun prepareViewModel() {
        val homeRepo = HomeRepositoryImp(OpenLibApiClient, LocalDataClient)
        val homeViewModelFactory = HomeViewModelFactory(homeRepo)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    private fun initComponents(view: View) {
        profileButton = view.findViewById(R.id.home_profile_btn)
        trendButton = view.findViewById(R.id.home_trend_btn)
        categoryButton = view.findViewById(R.id.home_category_btn)
        randomButton = view.findViewById(R.id.home_random_btn)
        randomTitleTextView = view.findViewById(R.id.home_random_title_tv)
        randomDescriptionTextView = view.findViewById(R.id.home_random_desc_tv)
        randomCoverImageView = view.findViewById(R.id.home_random_cover_iv)

        categoryRecyclerView = view.findViewById(R.id.home_category_rv)
        setupCategoryRecyclerView()

        trendRecyclerView = view.findViewById(R.id.home_trend_rv)
        setupTrendRecyclerView()

        authorRecyclerView = view.findViewById(R.id.home_authors_rv)
        setupAuthorRecyclerView()
    }

    private fun setupCategoryRecyclerView() {
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(listOf(), this)
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun setupTrendRecyclerView() {
        trendRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        trendAdapter = BookAdapter(listOf(), this, this.requireContext())
        trendRecyclerView.adapter = trendAdapter
    }

    private fun setupAuthorRecyclerView() {
        authorRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        authorAdapter = AuthorAdapter(listOf(), this, this.requireContext())
        authorRecyclerView.adapter = authorAdapter
    }

    private fun fetchData() {
        homeViewModel.categoryListLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.setDataToAdapter(
                it,
            )
        }
        homeViewModel.trendingListLiveData.observe(viewLifecycleOwner) {
            trendAdapter.setDataToAdapter(
                it.items!!,
            )
        }
        homeViewModel.randomBookListLiveData.observe(viewLifecycleOwner) {
            Log.d("MAHMOUD", "testRandomWork: $it")
            randomTitleTextView.text =
                "${it.title} ${(it.firstPublishDate).let { year -> if (year.isNullOrEmpty()) "" else "($year)" }}\n${
                    it.author?.joinToString(
                        ", "
                    )
                }"
            randomDescriptionTextView.text = "${it.description?.value?.clearSources() ?: ""}"

//

            Glide.with(this.requireContext())
                .asBitmap()
                .load(
                    if (it.book?.key.isNullOrEmpty()) {
                        UtilMethods.createCoverUrl(
                            "${it.covers?.first() ?: -1}",
                            CoverKey.ID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
                    } else {
                        UtilMethods.createCoverUrl(
                            it.book?.key!!,
                            CoverKey.OLID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
                    },
                )
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(randomCoverImageView)
        }
        homeViewModel.authorsListLiveData.observe(viewLifecycleOwner) {
            authorAdapter.setDataToAdapter(it)
        }

        homeViewModel.fetchCategoryList(11)
//        homeViewModel.fetchTrendingBooks(Trending.TODAY, limit = 10)
        homeViewModel.fetchRandomBook()
        homeViewModel.fetchAuthors(10)
    }

    override fun onCategoryItemClick(position: Int) {
        homeViewModel.handelCategoryItemClick(this.requireContext(), position)
    }

    override fun onBookItemClick(position: Int) {
        homeViewModel.handelBookItemClick(this.requireContext(), position)
    }

    override fun onAuthorItemClick(position: Int) {
        homeViewModel.handelAuthorItemClick(this.requireContext(), position)
    }
}
