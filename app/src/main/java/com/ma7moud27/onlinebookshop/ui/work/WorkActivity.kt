package com.ma7moud27.onlinebookshop.ui.work

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.repository.work.WorkRepoImpl
import com.ma7moud27.onlinebookshop.viewmodel.WorkViewModel
import com.ma7moud27.onlinebookshop.viewmodel.factory.WorkViewModelFactory

class WorkActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var workViewModel: WorkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
        prepareViewModel()
        initComponents()

//        workViewModel.fetchWork("OL82563W") // ph
//        workViewModel.fetchWork("OL82537W") // cha
//        workViewModel.fetchWork("OL82536W") // azk
//        workViewModel.fetchWork("OL82560W") // GOF
//        workViewModel.fetchWork("OL82548W") // order
//        workViewModel.fetchWork("OL82565W") // Half
//        workViewModel.fetchWork("OL82586W") // Death
//        workViewModel.workListLiveData.observe(this) {
//                works ->
//            works.forEach { work ->
//
//                textView.append(
//                    buildString {
//                        append("Title: ${work.title}\n\n")
//                        append("Description: ${work.description?.value}\n\n")
//                        append("People: ${work.people.joinToString("") { "\t\t$it\n" }}\n")
//                        append("Places: ${work.places.joinToString("") { "\t\t$it\n" }}\n")
//                        append("Subjects: ${work.subjects.joinToString("") { "\t\t$it\n" }}\n")
//                        append("Links: ${work.links.joinToString("") { "\t\t${it.title}\n\t\t\t${it.url}" }}\n")
//                        append("===================================\n\n")
//                    },
//                )
//            }
//        }
    }

    private fun prepareViewModel() {
        val workRepo = WorkRepoImpl(OpenLibApiClient)
        val workViewModelFactory = WorkViewModelFactory(workRepo)
        workViewModel = ViewModelProvider(this, workViewModelFactory)[WorkViewModel::class.java]
    }

    private fun initComponents() {
//        textView = findViewById(R.id.textView)
    }
}
