package com.ma7moud27.shelfy.ui.main.fragments.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.local.LocalDataClient
import com.ma7moud27.shelfy.model.ServiceResponse
import com.ma7moud27.shelfy.network.openlibrary.OpenLibApiClient
import com.ma7moud27.shelfy.ui.adapter.*
import com.ma7moud27.shelfy.ui.booksdisplay.view.BooksDisplayActivity
import com.ma7moud27.shelfy.ui.main.MainActivity
import com.ma7moud27.shelfy.ui.main.fragments.home.repository.HomeRepositoryImp
import com.ma7moud27.shelfy.ui.main.fragments.home.viewmodel.HomeViewModel
import com.ma7moud27.shelfy.ui.main.fragments.home.viewmodel.HomeViewModelFactory
import com.ma7moud27.shelfy.utils.Constants.Companion.SENDER
import com.ma7moud27.shelfy.utils.Constants.Companion.TRENDING
import com.ma7moud27.shelfy.utils.UtilMethods
import com.ma7moud27.shelfy.utils.UtilMethods.Companion.clearSources
import com.ma7moud27.shelfy.utils.enums.CoverKey
import com.ma7moud27.shelfy.utils.enums.CoverSize
import com.ma7moud27.shelfy.utils.enums.Trending

class HomeFragment :
    Fragment(),
    OnCategoryBookClickListener,
    OnBookItemClickListener,
    OnAuthorItemClickListener {

    private lateinit var logoutButton: ImageButton
    private lateinit var trendButton: Button
    private lateinit var categoryButton: Button
    private lateinit var randomButton: Button
    private lateinit var randomTitleTextView: TextView
    private lateinit var randomDescriptionTextView: TextView
    private lateinit var randomCoverImageView: ImageView
    private lateinit var greetingsTextView: TextView

    private lateinit var shimmerTrendingLayout: ShimmerFrameLayout
    private lateinit var shimmerRandomLayout: ShimmerFrameLayout
    private lateinit var randomLayout: LinearLayout

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryBookAdapter: CategoryBookAdapter
    private lateinit var trendRecyclerView: RecyclerView
    private lateinit var trendAdapter: BookAdapter
    private lateinit var authorRecyclerView: RecyclerView
    private lateinit var authorAdapter: AuthorAdapter

    private lateinit var homeViewModel: HomeViewModel

    private var userName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        prepareViewModel()
        initComponents(view)
        fetchData()

        logoutButton.setOnClickListener {
            AlertDialog.Builder(this@HomeFragment.requireContext()).setTitle("Logout")
                .setMessage("You will be logged out from the application\nAre you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ -> homeViewModel.logout(this@HomeFragment.requireContext()) }
                .setNegativeButton("No", null).show()
        }
        trendButton.setOnClickListener {
            startActivity(
                Intent(this.requireContext(), BooksDisplayActivity::class.java).apply {
                    putExtra(SENDER, TRENDING)
                },
            )
        }
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
                randomCoverImageView,
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
        logoutButton = view.findViewById(R.id.home_profile_btn)
        trendButton = view.findViewById(R.id.home_trend_btn)
        categoryButton = view.findViewById(R.id.home_category_btn)
        randomButton = view.findViewById(R.id.home_random_btn)
        randomTitleTextView = view.findViewById(R.id.home_random_title_tv)
        randomDescriptionTextView = view.findViewById(R.id.home_random_desc_tv)
        randomCoverImageView = view.findViewById(R.id.home_random_cover_iv)
        greetingsTextView = view.findViewById(R.id.home_greetings_tv)

        shimmerTrendingLayout = view.findViewById(R.id.home_trending_shimmer_layout)
        shimmerRandomLayout = view.findViewById(R.id.home_random_shimmer_layout)
        randomLayout = view.findViewById(R.id.home_random_layout)

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
        categoryBookAdapter = CategoryBookAdapter(listOf(), this)
        categoryRecyclerView.adapter = categoryBookAdapter
    }

    private fun setupTrendRecyclerView() {
        trendRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        trendAdapter =
            BookAdapter(listOf(), this, this.requireContext(), R.layout.item_book_vertical)
        trendRecyclerView.adapter = trendAdapter
    }

    private fun setupAuthorRecyclerView() {
        authorRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        authorAdapter = AuthorAdapter(listOf(), this, this.requireContext())
        authorRecyclerView.adapter = authorAdapter
    }

    private fun fetchData() {
        greetingsTextView.text =
            "Good Morning, ${homeViewModel.getName()?.split(" ")?.get(0) ?: ""}!"

        homeViewModel.categoryListLiveData.observe(viewLifecycleOwner) {
            categoryBookAdapter.setDataToAdapter(
                it.data!!,
            )
        }
        homeViewModel.trendingListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ServiceResponse.Loading -> shimmerTrendingLayout.startShimmer()
                else -> {
                    trendAdapter.setDataToAdapter(
                        it.data?.items!!,
                    )
                    shimmerTrendingLayout.apply {
                        stopShimmer()
                        visibility = View.INVISIBLE
                    }
                }
            }
        }
        homeViewModel.randomBookListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ServiceResponse.Loading -> shimmerRandomLayout.startShimmer()
                else -> {
                    randomTitleTextView.text =
                        "${it.data?.title} ${(it.data?.firstPublishDate).let { year -> if (year.isNullOrEmpty()) "" else "($year)" }}\n${
                            it.data?.author?.joinToString(", ")
                        }"
                    randomDescriptionTextView.text =
                        "${it.data?.description?.value?.clearSources() ?: ""}"
                    shimmerRandomLayout.stopShimmer()
                    shimmerRandomLayout.visibility = View.INVISIBLE
                    randomLayout.visibility = View.VISIBLE
                }
            }

            Glide.with(this.requireContext()).asBitmap().load(
                if (it.data?.book?.key.isNullOrEmpty()) {
                    UtilMethods.createCoverUrl(
                        "${it.data?.covers?.first() ?: -1}",
                        CoverKey.ID.name.lowercase(),
                        CoverSize.MEDIUM.query,
                    )
                } else {
                    UtilMethods.createCoverUrl(
                        it.data?.book?.key!!,
                        CoverKey.OLID.name.lowercase(),
                        CoverSize.MEDIUM.query,
                    )
                },
            ).diskCacheStrategy(DiskCacheStrategy.DATA).into(randomCoverImageView)
        }
        homeViewModel.authorsListLiveData.observe(viewLifecycleOwner) {
            authorAdapter.setDataToAdapter(it.data!!)
        }

        homeViewModel.fetchCategoryList(11)
        homeViewModel.fetchTrendingBooks(Trending.TODAY, limit = 10)
        homeViewModel.fetchRandomBook()
        homeViewModel.fetchAuthors(10)
    }

    override fun onCategoryBookClick(position: Int) {
        homeViewModel.handelCategoryItemClick(this.requireContext(), position)
    }

    override fun onBookItemClick(holder: BookAdapter.BookViewHolder, position: Int) {
        homeViewModel.handelBookItemClick(
            this.requireContext(),
            position,
            holder.titleTextView,
            holder.coverImageView,
        )
    }

    override fun onAuthorItemClick(position: Int) {
        homeViewModel.handelAuthorItemClick(this.requireContext(), position)
    }
}
