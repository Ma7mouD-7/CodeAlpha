package com.ma7moud27.exams.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ma7moud27.exams.utils.Constants
import com.ma7moud27.exams.utils.enums.UserType

@Entity(tableName = Constants.USER_DATABASE)
data class User(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val email: String = "",
    val type: UserType? = null,
)
