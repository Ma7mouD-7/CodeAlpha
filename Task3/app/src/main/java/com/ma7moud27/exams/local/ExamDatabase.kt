package com.ma7moud27.exams.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ma7moud27.exams.model.Converters
import com.ma7moud27.exams.model.Exam

@Database(entities = [Exam::class], version = 1)
@TypeConverters(Converters::class)
abstract class ExamDatabase : RoomDatabase() {
    abstract fun ExamDao(): ExamDao

    companion object {
        @Volatile
        private var INSTANCE: ExamDatabase? = null

        fun getInstance(context: Context): ExamDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ExamDatabase::class.java,
                    "exam_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
