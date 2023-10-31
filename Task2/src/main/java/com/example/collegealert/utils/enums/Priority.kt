package com.example.collegealert.utils.enums

import com.example.collegealert.R

enum class Priority(val colorId: Int) {
    IMPORTANT(R.color.red),
    NORMAL(R.color.yellow),
    NOT_IMPORTANT(R.color.green),
}
