package com.ma7moud27.onlinebookshop.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.ma7moud27.onlinebookshop.utils.enums.CoverKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class UtilMethods {
    companion object {
        fun createCoverUrl(id: String, key: String, size: String) =
            "${Constants.OPEN_LIBRARY_BOOK_COVER_BASE_URL}$key/$id-$size.jpg"
        fun createAuthorPicUrl(id: String, key: String = CoverKey.OLID.name.lowercase(), size: String) =
            "${Constants.OPEN_LIBRARY_AUTHOR_PIC_BASE_URL}$key/$id-$size.jpg"

        @RequiresApi(Build.VERSION_CODES.O)
        fun calculateAge(birthDate: String, deathDate: String): String? {
            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
            return try {
                val birthLocalDate = LocalDate.parse(birthDate, formatter)
                val deathLocalDate = LocalDate.parse(deathDate, formatter)
                val age = ChronoUnit.YEARS.between(birthLocalDate, deathLocalDate)
                " (at $age years)"
            } catch (e: java.time.format.DateTimeParseException) {
                null
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun calculateAge(birthDate: String): String? {
            return try {
                val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
                val birthLocalDate = LocalDate.parse(birthDate, formatter)
                val currentDate = LocalDate.now()
                val age = ChronoUnit.YEARS.between(birthLocalDate, currentDate)
                " ($age years)"
            } catch (e: java.time.format.DateTimeParseException) {
                null
            }
        }

        fun String.clearSources() = this
            .substringBefore("\\r\\n\\r\\n(")
            .substringBefore("\\r\\n([so")
            .substringBefore("\\r\\n\\r\\n([s")
            .substringBefore("<su")
            .substringBefore("---")
            .substringBefore("\\r\\n\\r\\n\\r\\n  [1]:")
            .substringBefore("[1]:")

        fun String.extractIdFromKey() = this.substringAfterLast("/")
    }
}
