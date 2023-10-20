package com.ma7moud27.shelfy.utils.enums

import android.graphics.Color
import androidx.annotation.ColorInt
import com.ma7moud27.shelfy.R

const val WHITE = "#FFFFFF"

enum class Category(
    val query: String,
    @ColorInt val color: Int,
    val height: Double,
    val width: Double,
    val description: String,
    val icon: Int
) {
    FANTASY("fantasy", Color.parseColor("#EF9A9A"), 1.02, 1.15, "Something invented by imagination or feigned", R.drawable.category_fantasy),
    HORROR("horror", Color.parseColor("#7D9E57"), 0.95, 1.0, "Meant to cause discomfort and fear for both characters and readers, by making use of supernatural elements in morbid stories that maybe realistic", R.drawable.category_horror),
    MAGIC("magic", Color.parseColor("#85A0FF"), 0.9, 0.9, "Set in imaginary universe, with supernatural and magical creatures  are common", R.drawable.category_magic),
    DETECTIVES("mystery_and_detective_stories", Color.parseColor("#F06292"), 1.1, 1.25, "The plot always revolves around a crime of sorts that must be solved by the protagonists", R.drawable.category_detective),
    THRILLER("thriller", Color.parseColor("#FFCDD2"), 1.03, 1.0, "Keeps You aon the edge of your seat with excitement, as your fave character finds themselves in high stakes situations", R.drawable.category_thriller),
    SCIFI("science_fiction", Color.parseColor("#B2EBF2"), 1.06, 1.2, "Fantasy stories that lean heavily on themes of technology and future science", R.drawable.category_scifi),
    ROMANCE("romance", Color.parseColor("#F18FF4"), 1.0, 1.3, "Makes your heart all warm and fuzzy focuses on the love story of the main protagonists", R.drawable.category_romance),
    LITERATURE("literature", Color.parseColor("#9E7657"), 1.07, 1.1, "Collection of written works sharing characteristics in the same category", R.drawable.category_literature),
    HISTORY("history", Color.parseColor("#55D647"), 1.1, 1.5, "Focusing on educating and informing the reader, looking at all parts of the world at any given moment", R.drawable.category_history),
    BIOGRAPHY("biography", Color.parseColor("#7C7C7C"), 1.0, 1.3, "Serving as an official account of the details and events of a person's life span", R.drawable.category_biography),
    SHORT_STORIES("short_stories", Color.parseColor(WHITE), 1.0, 1.2, "Strictly tells through a specific theme and a series of brief scenes", R.drawable.category_story),

    MUSIC("music", Color.parseColor(WHITE), 1.0, 1.0, "Identifies pieces of music as belonging to a shared tradition or set of conventions", R.drawable.category_music),
    FASHION("fashion", Color.parseColor(WHITE), 1.0, 1.0, "About popular trends in clothing, make-up, styles, accessories, ornaments, and manner of behaviour", R.drawable.category_fashion),
    FILM("film", Color.parseColor(WHITE), 1.0, 1.0, "About films, documentaries, or the process of making a film or documentary", R.drawable.category_film),
    ART("painting", Color.parseColor(WHITE), 1.0, 1.0, "Tells about some sort of artistic forms: painting, sculpting", R.drawable.category_art),

    BIOLOGY("biology", Color.parseColor(WHITE), 1.0, 1.0, "Branch of natural science that deals with living organisms and their vital processes", R.drawable.category_biology),
    CHEMISTRY("chemistry", Color.parseColor(WHITE), 1.0, 1.0, "Branch of natural science that deals principally with the properties of substances and their changes", R.drawable.category_chemistry),
    MATHEMATICS("mathematics", Color.parseColor(WHITE), 1.0, 1.0, "The science of structure, order, and relation that has evolved from elemental practices of counting measuring", R.drawable.category_math),
    PHYSICS("physics", Color.parseColor(WHITE), 1.0, 1.0, "Branch of natural science that deals with the structure of matter and how the fundamental constituents of the universe interact", R.drawable.category_physics),
    PROGRAMMING("programming", Color.parseColor(WHITE), 1.0, 1.0, "Language of machines, Focuses on how to deal with computers and how to use them", R.drawable.category_programming),
    BUSINESS("business", Color.parseColor(WHITE), 1.0, 1.0, "The management and running of a business, or in the financial aspects of a business", R.drawable.category_business),
    TEXTBOOKS("textbooks", Color.parseColor(WHITE), 1.0, 1.0, "Educational Books whose its primary purpose is to educate and inform people", R.drawable.category_textbook),

    PSYCHOLOGY("psychology", Color.parseColor(WHITE), 1.0, 1.0, "Study of mind and behaviours in humans and non-humans, including feelings and thoughts", R.drawable.category_psychology),
    COOKING("cooking", Color.parseColor(WHITE), 1.0, 1.0, "Penned by professional chef, offers an appetizing collection of recipes, specific to a theme, cuisines", R.drawable.category_cooking),

    POETRY("poetry", Color.parseColor(WHITE), 1.0, 1.0, "Authors choose a particular rhythm and style to evoke and portray various emotions and ideas", R.drawable.category_poetry),
    PLAYS("plays", Color.parseColor(WHITE), 1.0, 1.0, "Form of drama that primarily consists of dialogue between characters and is intended for theatrical performances rather than more reading", R.drawable.category_plays),
}
