package com.ma7moud27.exams.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ma7moud27.exams.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("SELECT * FROM users")
    fun getAllCreators(): List<User>

    @Query("SELECT * FROM users WHERE id =:id")
    fun getUser(id: String): User
}
