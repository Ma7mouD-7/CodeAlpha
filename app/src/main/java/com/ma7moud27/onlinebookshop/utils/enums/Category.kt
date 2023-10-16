package com.ma7moud27.onlinebookshop.utils.enums

import android.graphics.Color
import androidx.annotation.ColorInt

enum class Category(val query: String, @ColorInt val color: Int, val height: Double, val width: Double, val description: String) {
    FANTASY("fantasy", Color.parseColor("#EF9A9A"), 1.02, 1.15, "Something invented by imagination or feigned"),
    HORROR("horror", Color.parseColor("#7D9E57"), 0.95, 1.0, "Meant to cause discomfort and fear for both characters and readers, by making use of supernatural elements in morbid stories that maybe realistic"),
    MAGIC("magic", Color.parseColor("#85A0FF"), 0.9, 0.9, "Set in imaginary universe, with supernatural and magical creatures  are common"),
    DETECTIVES("mystery_and_detective_stories", Color.parseColor("#F06292"), 1.1, 1.25, "The plot always revolves around a crime of sorts that must be solved by the protagonists"),
    THRILLER("thriller", Color.parseColor("#FFCDD2"), 1.03, 1.0, "Keeps You aon the edge of your seat with excitement, as your fave character finds themselves in high stakes situations"),
    SCIFI("science_fiction", Color.parseColor("#B2EBF2"), 1.06, 1.2, "Fantasy stories that lean heavily on themes of technology and future science"),
    ROMANCE("romance", Color.parseColor("#F18FF4"), 1.0, 1.3, "Makes your heart all warm and fuzzy focuses on the love story of the main protagonists"),
    LITERATURE("literature", Color.parseColor("#9E7657"), 1.07, 1.1, "Collection of written works sharing characteristics in the same category"),
    HISTORY("history", Color.parseColor("#55D647"), 1.1, 1.5, "Focusing on educating and informing the reader, looking at all parts of the world at any given moment"),
    BIOGRAPHY("biography", Color.parseColor("#7C7C7C"), 1.0, 1.3, "Serving as an official account of the details and events of a person's life span"),
    RELIGION("religion", Color.parseColor("#FFFFFF"), 1.08, 1.2, "Authoritative source or divinely inspired statement of its faith, history, and practices"),

    MUSIC("music", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Identifies pieces of music as belonging to a shared tradition or set of conventions"),
    FASHION("fashion", Color.parseColor("#FFFFFF"), 1.0, 1.0, "About popular trends in clothing, make-up, styles, accessories, ornaments, and manner of behaviour"),
    FILM("film", Color.parseColor("#FFFFFF"), 1.0, 1.0, "About films, documentaries, or the process of making a film or documentary"),
    ART("painting", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Tells about some sort of artistic forms: painting, sculpting"),

    BIOLOGY("biology", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Branch of natural science that deals with living organisms and their vital processes"),
    CHEMISTRY("chemistry", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Branch of natural science that deals principally with the properties of substances and their changes"),
    MATHEMATICS("mathematics", Color.parseColor("#FFFFFF"), 1.0, 1.0, "The science of structure, order, and relation that has evolved from elemental practices of counting measuring"),
    PHYSICS("physics", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Branch of natural science that deals with the structure of matter and how the fundamental constituents of the universe interact"),
    PROGRAMMING("programming", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Language of machines, Focuses on how to deal with computers and how to use them"),
    BUSINESS("business", Color.parseColor("#FFFFFF"), 1.0, 1.0, "The management and running of a business, or in the financial aspects of a business"),
    TEXTBOOKS("textbooks", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Educational Books whose its primary purpose is to educate and inform people"),

    PSYCHOLOGY("psychology", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Study of mind and behaviours in humans and non-humans, including feelings and thoughts"),
    COOKING("cooking", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Penned by professional chef, offers an appetizing collection of recipes, specific to a theme, cuisines"),

    SHORT_STORIES("short_stories", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Strictly tells through a specific theme and a series of brief scenes"),
    POETRY("poetry", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Authors choose a particular rhythm and style to evoke and portray various emotions and ideas"),
    PLAYS("plays", Color.parseColor("#FFFFFF"), 1.0, 1.0, "Form of drama that primarily consists of dialogue between characters and is intended for theatrical performances rather than more reading"),
}
