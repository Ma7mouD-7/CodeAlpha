package com.ma7moud27.exams.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ma7moud27.exams.model.Result

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(result: Result)

    @Update
    fun update(result: Result)

    @Delete
    fun delete(result: Result)
    @Query("DELETE FROM results")
    fun deleteAll()

    @Query("SELECT * FROM results")
    fun getAllResults(): List<Result>

    @Query("SELECT * FROM results WHERE id =:id")
    fun getResult(id: Int): Result
}
