package com.example.collegealert.utils.enums

import com.example.collegealert.R

/*
* Created by Ma7mouD on Mon 30/10/2023 at 05:57 AM.
*/
enum class TaskType(val color: Int, val icon: Int) {
    UPCOMING(R.color.gold, R.drawable.reloading),
    DONE(R.color.brown, R.drawable.check),
    PASSED(R.color.grey_blue, R.drawable.cancel),
}
