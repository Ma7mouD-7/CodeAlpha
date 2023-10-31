package com.example.collegealert.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.collegealert.R
import com.example.collegealert.local.DatabaseClient
import com.example.collegealert.ui.adapter.ViewPagerAdapter
import com.example.collegealert.ui.main.fragments.DoneFragment
import com.example.collegealert.ui.main.fragments.PassedFragment
import com.example.collegealert.ui.main.fragments.UpcomingFragment
import com.example.collegealert.ui.main.repository.MainRepositoryImp
import com.example.collegealert.ui.main.viewmodel.MainViewModel
import com.example.collegealert.ui.main.viewmodel.MainViewModelFactory
import com.example.collegealert.ui.task.view.TaskActivity
import com.example.collegealert.utils.enums.TaskType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var addButton: FloatingActionButton

    private lateinit var doneFragment: Fragment
    private lateinit var passedFragment: Fragment
    private lateinit var upcomingFragment: Fragment

    private lateinit var pagerAdapter: ViewPagerAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()

        viewModel.fetchTasksFromDatabase()

        addButton.setOnClickListener {
            startActivity(
                Intent(this, TaskActivity::class.java),
            )
        }
    }

    private fun initComponents() {
        tabLayout = findViewById(R.id.main_tab_layout)
        viewPager = findViewById(R.id.main_view_pager)
        addButton = findViewById(R.id.main_add_btn)

        initFragments()
        prepareViewModel()
        setupViewPager()
    }

    private fun initFragments() {
        doneFragment = DoneFragment()
        passedFragment = PassedFragment()
        upcomingFragment = UpcomingFragment()
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                this.application,
                MainRepositoryImp(DatabaseClient),
            ),
        )[MainViewModel::class.java]
    }

    private fun setupViewPager() {
        pagerAdapter = ViewPagerAdapter(
            listOf(
                upcomingFragment,
                doneFragment,
                passedFragment,
            ),
            supportFragmentManager,
            this.lifecycle,
        )
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TaskType.values()[position].name.lowercase().replaceFirstChar { it.uppercase() }
            tab.icon = ContextCompat.getDrawable(this, TaskType.values()[position].icon)
        }.attach()
        viewPager.currentItem = 0
        viewPager.isUserInputEnabled = false
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTasksFromDatabase()
    }
}
