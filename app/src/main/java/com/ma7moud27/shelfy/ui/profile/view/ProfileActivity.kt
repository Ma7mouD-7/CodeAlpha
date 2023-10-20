package com.ma7moud27.shelfy.ui.profile.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.local.LocalDataClient
import com.ma7moud27.shelfy.network.openlibrary.OpenLibApiClient
import com.ma7moud27.shelfy.ui.profile.repository.ProfileRepositoryImp
import com.ma7moud27.shelfy.ui.profile.viewmodel.ProfileViewModel
import com.ma7moud27.shelfy.ui.profile.viewmodel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        prepareViewModel()
        initComponents()
    }

    private fun prepareViewModel() {
        val profileRepository = ProfileRepositoryImp(OpenLibApiClient, LocalDataClient)
        val profileViewModelFactory = ProfileViewModelFactory(profileRepository)
        profileViewModel = ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]
    }
    private fun initComponents() {
//        TODO("Not yet implemented")
    }
}
