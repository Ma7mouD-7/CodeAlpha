package com.example.collegealert.utils

import android.text.Editable
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
* Created by Ma7mouD on Mon 30/10/2023 at 11:12 PM.
*/
object UtilMethods {
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun compareDates(dateString: String, timeString: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate1 = dateFormat.format(SimpleDateFormat("dd, MMM yyyy", Locale.getDefault()).parse(dateString))
        val formattedDate2 = dateFormat.format(Date())
        val compareTo = formattedDate1.compareTo(formattedDate2)
        Log.d("COMPARE DATE", "compareDates: $compareTo")
        return if (compareTo == 0) {
            compareTime(timeString)
        } else {
            compareTo < 0
        }
    }

    fun compareTime(timeString: String): Boolean {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val formattedTime1 = timeFormat.format(SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(timeString))
        val formattedTime2 = timeFormat.format(Date())
        return formattedTime1.compareTo(formattedTime2) <= 0
    }
}
