package com.ma7moud27.exams.network.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ma7moud27.exams.model.Exam
import com.ma7moud27.exams.model.Result
import com.ma7moud27.exams.model.User
import com.ma7moud27.exams.utils.Constants.Companion.EXAM_DATABASE
import com.ma7moud27.exams.utils.Constants.Companion.RESULT_DATABASE
import com.ma7moud27.exams.utils.Constants.Companion.USER_DATABASE

object FirebaseFirestoreClient {
    private val database: FirebaseFirestore by lazy { Firebase.firestore }

    fun addUser(userID: String, user: User) = database.collection(USER_DATABASE).document(userID).set(user)
    fun getUser(userID: String) = database.collection(USER_DATABASE).document(userID).get()
    fun updateUser(userID: String, user: User) = database.collection(USER_DATABASE).document(userID).set(user, SetOptions.merge())

    fun addExam(exam: Exam) = database.collection(EXAM_DATABASE).document(exam.code).set(exam)
    fun getAllExams() = database.collection(EXAM_DATABASE).get()
    fun getExamsOfCreator(creatorID: String) = database.collection(EXAM_DATABASE).whereEqualTo("creatorID", creatorID).get()
    fun getExam(examCode: String) = database.collection(EXAM_DATABASE).document(examCode).get()
    fun updateExam(exam: Exam) = database.collection(EXAM_DATABASE).document(exam.code).set(exam, SetOptions.merge())

    fun addResult(result: Result) = database.collection(RESULT_DATABASE).add(result)
    fun getAllResults() = database.collection(RESULT_DATABASE).get()
    fun getResultsOfTaker(takerID: String) = database.collection(RESULT_DATABASE).whereEqualTo("takerId", takerID).get()
    fun getResultsOfCreator(creatorId: String) = database.collection(RESULT_DATABASE).whereEqualTo("creatorId", creatorId).get()
    fun getResultsOfExam(examCode: String) = database.collection(RESULT_DATABASE).whereEqualTo("examCode", examCode).get()
//    fun updateResult(examCode: String,takerID : String, result: Result) =
//        database.collection(RESULT_DATABASE)
//            .whereEqualTo("takerId", takerID)
//            .whereEqualTo("examCode", examCode)
//            .get().addOnCompleteListener {
//
//            }
//        .set(result, SetOptions.merge())
}
