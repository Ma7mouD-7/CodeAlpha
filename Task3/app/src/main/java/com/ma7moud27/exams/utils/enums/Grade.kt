package com.ma7moud27.exams.utils.enums

import android.graphics.Color

enum class Grade(
    val letter: String,
    val min: Int,
    val max: Int,
    val color: Int,
) {
    S("S", 99, 101, Color.parseColor("#FFF")),
    A_PLUS("A+", 95, 99, Color.parseColor("#FFF")),
    A("A", 91, 95, Color.parseColor("#FFF")),
    A_MINUS("A-", 87, 91, Color.parseColor("#FFF")),
    B_PLUS("B+", 83, 87, Color.parseColor("#FFF")),
    B("B", 79, 83, Color.parseColor("#FFF")),
    B_MINUS("B-", 75, 79, Color.parseColor("#FFF")),
    C_PLUS("C+", 71, 75, Color.parseColor("#FFF")),
    C("C", 67, 71, Color.parseColor("#FFF")),
    C_MINUS("C-", 63, 67, Color.parseColor("#FFF")),
    D_PLUS("D+", 59, 63, Color.parseColor("#FFF")),
    D("D", 55, 59, Color.parseColor("#FFF")),
    D_MINUS("D-", 52, 55, Color.parseColor("#FFF")),
    E("E", 50, 52, Color.parseColor("#FFF")),
    F("F", 0, 50, Color.parseColor("#FFF")),
}
