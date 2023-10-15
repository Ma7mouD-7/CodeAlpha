package com.ma7moud27.onlinebookshop.utils.enums

import android.graphics.Color
import androidx.annotation.ColorInt

enum class Category(val query: String, @ColorInt val color: Int, val height: Double, val width: Double) {
    FANTASY("fantasy", Color.parseColor("#EF9A9A"), 1.02, 1.15),
    HORROR("horror", Color.parseColor("#7D9E57"), 0.95, 1.0),
    MAGIC("magic", Color.parseColor("#85A0FF"), 0.9, 0.9),
    DETECTIVES("mystery_and_detective_stories", Color.parseColor("#F06292"), 1.1, 1.25),
    THRILLER("thriller", Color.parseColor("#FFCDD2"), 1.03, 1.0),
    SCIFI("science_fiction", Color.parseColor("#B2EBF2"), 1.06, 1.2),
    ROMANCE("romance", Color.parseColor("#F18FF4"), 1.0, 1.3),
    LITERATURE("literature", Color.parseColor("#9E7657"), 1.07, 1.1),
    HISTORY("history", Color.parseColor("#55D647"), 1.1, 1.5),
    BIOGRAPHY("biography", Color.parseColor("#7C7C7C"), 1.0, 1.3),
    RELIGION("religion", Color.parseColor("#FFFFFF"), 1.08, 1.2),

    MUSIC("music", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    FASHION("fashion", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    FILM("film", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    PAINTING("painting", Color.parseColor("#FFFFFF"), 1.0, 1.0),

    BIOLOGY("biology", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    CHEMISTRY("chemistry", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    MATHEMATICS("mathematics", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    PHYSICS("physics", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    PROGRAMMING("programming", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    BUSINESS("business", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    TEXTBOOKS("textbooks", Color.parseColor("#FFFFFF"), 1.0, 1.0),

    PSYCHOLOGY("psychology", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    COOKING("cooking", Color.parseColor("#FFFFFF"), 1.0, 1.0),

    SHORT_STORIES("short_stories", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    POETRY("poetry", Color.parseColor("#FFFFFF"), 1.0, 1.0),
    PLAYS("plays", Color.parseColor("#FFFFFF"), 1.0, 1.0),
}
