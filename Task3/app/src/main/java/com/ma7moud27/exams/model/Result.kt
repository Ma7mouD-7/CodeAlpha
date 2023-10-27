package com.ma7moud27.exams.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ma7moud27.exams.utils.Constants

@Entity(tableName = Constants.RESULT_DATABASE)
data class Result(
    @PrimaryKey val id: Int = 0,
    val examCode: String = "",
    val takerId: String = "",
    val creatorId: String = "",
    val answers: MutableList<String> = mutableListOf(),
    val grade: Double =0.0,
)
