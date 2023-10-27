package com.ma7moud27.exams.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ma7moud27.exams.model.Converters
import com.ma7moud27.exams.model.Result

@Database(entities = [Result::class], version = 1)
@TypeConverters(Converters::class)
abstract class ResultDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        fun getInstance(context: Context): ResultDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ResultDatabase::class.java,
                    "result_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
