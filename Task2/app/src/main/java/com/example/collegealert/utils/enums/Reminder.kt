package com.example.collegealert.utils.enums

/*
* Created by Ma7mouD on Mon 30/10/2023 at 10:28 PM.
*/
enum class Reminder(val value: String, val minutes: Int) {
    NONE("None", 0),
    ONE_MINUTE("1 Minute", 1),
    TEN_MINUTES("10 Minutes", 10),
    THIRTY_MINUTES("30 Minutes", 30),
    ONE_HOUR("1 Hour", 1 * 60),
    TWO_HOUR("2 Hour", 2 * 60),
    FIVE_HOUR("5 Hour", 5 * 60),
    EIGHT_HOUR("8 Hour", 8 * 60),
    TWELVE_HOUR("12 Hour", 12 * 60),
    ONE_DAY("1 Day", 1 * 24 * 60),
    THREE_DAY("3 Day", 3 * 24 * 60),
    WEEK("1 Week", 7 * 24 * 60),
}
