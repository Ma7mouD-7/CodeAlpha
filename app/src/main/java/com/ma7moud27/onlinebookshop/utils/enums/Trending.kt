package com.ma7moud27.onlinebookshop.utils.enums

enum class Trending(val query: String) {
    NOW("now"),
    TODAY("daily"),
    THIS_WEEK("weekly"),
    THIS_MONTH("monthly"),
    THIS_YEAR("yearly"),
    ALL_TIME("forever")
}