package com.ma7moud27.exams.ui.main.presenter

import android.content.Context
import android.view.View
import com.google.firebase.firestore.ktx.toObject
import com.ma7moud27.exams.R
import com.ma7moud27.exams.local.ExamDatabase
import com.ma7moud27.exams.local.ResultDatabase
import com.ma7moud27.exams.local.UserDatabase
import com.ma7moud27.exams.model.Result
import com.ma7moud27.exams.model.User
import com.ma7moud27.exams.network.firebase.FirebaseAuthClient
import com.ma7moud27.exams.network.firebase.FirebaseFirestoreClient
import com.ma7moud27.exams.ui.main.view.MainView
import com.ma7moud27.exams.utils.enums.UserType
import java.util.concurrent.Executors

class MainPresenter(private val view: MainView) : IMainPresenter {
    override fun currentUser(userType: UserType) {
        FirebaseFirestoreClient.getUser(FirebaseAuthClient.currentUser()!!.uid).addOnSuccessListener {
            val user = it.toObject<User>()
            if (user != null) {
                getData(user, userType)
            }
            setViews(userType)
        }
    }

    override fun setProgressBarVisibility(isVisible: Boolean) {
        view.onSetProgressBar(isVisible)
    }

    override fun setViews(userType: UserType) {
        when (userType) {
            UserType.TAKER -> view.setViews(View.VISIBLE, View.GONE)
            UserType.CREATOR -> view.setViews(View.GONE, View.VISIBLE)
        }
    }

    override fun logout(context: Context) {
        FirebaseAuthClient.logout()
        Executors.newSingleThreadExecutor().execute {
            ExamDatabase.getInstance(context).ExamDao().deleteAll()
            UserDatabase.getInstance(context).userDao().deleteAll()
            ResultDatabase.getInstance(context).resultDao().deleteAll()
        }
        view.logout()
    }

    private fun getData(user: User, userType: UserType) {
        when (userType) {
            UserType.TAKER -> {
                FirebaseFirestoreClient.getResultsOfTaker(user.id).addOnSuccessListener { querySnapshot ->
                    val results: MutableList<Result> = mutableListOf()
                    for (doc in querySnapshot) {
                        val res = doc.toObject<Result>()
                        results.add(res)
                    }
                    view.setDataToViews(
                        user.name,
                        R.string.total_taken_exams,
                        results.fold(0.0) { acc, result -> acc + result.grade } / results.size,
                        results.sortedByDescending { it.grade }.take(5),
                        results.reversed().take(5),
                    )
                }
            }
            UserType.CREATOR -> {
                FirebaseFirestoreClient.getResultsOfCreator(user.id).addOnSuccessListener { querySnapshot ->
                    val results: MutableList<Result> = mutableListOf()
                    for (doc in querySnapshot) {
                        val res = doc.toObject<Result>()
                        results.add(res)
                    }
                    view.setDataToViews(
                        user.name,
                        R.string.total_created_exams,
                        results.fold(0.0) { acc, result -> acc + result.grade } / results.size,
                        results.sortedByDescending { it.grade }.take(5),
                        results.reversed().take(5),
                    )
                }
            }
        }
    }
}
