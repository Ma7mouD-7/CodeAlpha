package com.ma7moud27.shelfy.utils.enums

enum class BookSort(val query: String) {
    RELEVANCE(""),
    MOST_EDITIONS("editions"),
    FIRST_PUBLISHED("old"),
    MOST_RECENT("new"),
    TOP_RATED("rating"),
    READING_LOG("readinglog"),
    RANDOM("random")
}