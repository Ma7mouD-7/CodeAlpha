package com.example.collegealert.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.collegealert.utils.Constants
import com.example.collegealert.utils.enums.Priority
import com.example.collegealert.utils.enums.Reminder
import com.example.collegealert.utils.enums.TaskField
import com.example.collegealert.utils.enums.TaskType
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constants.TASK_DATABASE_NAME)
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String = "",
    val addedDate: String = "",
    val deadlineDate: String = "",
    val deadlineStartTime: String = "",
    val deadlineEndTime: String = "",
    val description: String = "",
    val location: String = "",
    val reminder: Reminder = Reminder.NONE,
    val field: TaskField = TaskField.MILESTONE,
    var type: TaskType = TaskType.UPCOMING,
    val priority: Priority = Priority.NORMAL,
    val isDone: Boolean = false
) : Parcelable
