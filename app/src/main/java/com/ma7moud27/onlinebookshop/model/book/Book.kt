package com.ma7moud27.onlinebookshop.model.book

import com.google.gson.annotations.SerializedName
import com.ma7moud27.onlinebookshop.model.work.Description

data class Book(
    val key: String = "",
    val title: String = "",
    val description: Description? = null,

    val contributors: List<Contributor> = listOf(),
    val covers: List<Int> = listOf(),

    @SerializedName("edition_name")
    val editionName: String = "",

    @SerializedName("isbn_10")
    val isbn10: List<String> = listOf(),

    @SerializedName("isbn_13")
    val isbn13: List<String> = listOf(),

    val languages: List<Language> = listOf(),
    val notes: Notes = Notes(),
    @SerializedName("number_of_pages")
    val numberOfPages: Int = 0,
    @SerializedName("physical_format")
    val physicalFormat: String = "",
    @SerializedName("publish_date")
    val publishDate: String = "",
    val publishers: List<String> = listOf(),
    val series: List<String> = listOf(),
    @SerializedName("table_of_contents")
    val tableOfContents: List<TableOfContent> = listOf(),
)
