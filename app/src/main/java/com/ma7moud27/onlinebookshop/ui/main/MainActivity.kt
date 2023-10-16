package com.ma7moud27.onlinebookshop.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.ui.main.fragments.categories.view.CategoriesFragment
import com.ma7moud27.onlinebookshop.ui.main.fragments.home.view.HomeFragment
import com.ma7moud27.onlinebookshop.ui.main.fragments.search.view.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var home: Fragment
    lateinit var categories: Fragment
    private lateinit var search: Fragment
    private lateinit var lists: Fragment

    private lateinit var chipBottomNavBar: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()

        chipBottomNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.main_menu_home -> replaceFragment(home)
                R.id.main_menu_search -> replaceFragment(search)
                R.id.main_menu_categories -> replaceFragment(categories)
                R.id.main_menu_lists -> replaceFragment(lists)
                else -> Toast.makeText(this, "Item no selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initComponents() {
        initFragments()
        chipBottomNavBar = findViewById(R.id.menu)
        replaceFragment(home, R.id.main_menu_home)
    }

    private fun initFragments() {
        home = HomeFragment()
        categories = CategoriesFragment()
        search = SearchFragment()
        lists = Fragment()
    }

    fun replaceFragment(fragment: Fragment, mainMenuHome: Int? = null) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_frame, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
        if (mainMenuHome != null) chipBottomNavBar.setItemSelected(mainMenuHome, true)
    }
}
