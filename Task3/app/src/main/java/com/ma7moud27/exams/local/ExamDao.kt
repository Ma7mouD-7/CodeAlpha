package com.ma7moud27.exams.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ma7moud27.exams.model.Exam

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exam: Exam)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(exam: Exam)

    @Delete
    fun delete(exam: Exam)

    @Query("DELETE FROM exams")
    fun deleteAll()

    @Query("SELECT * FROM exams")
    fun getAllExams(): List<Exam>

    @Query("SELECT * FROM exams WHERE id =:id")
    fun getExam(id: Int): Exam
}
