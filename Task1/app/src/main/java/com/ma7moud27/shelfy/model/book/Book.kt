package com.ma7moud27.shelfy.model.book

import com.google.gson.annotations.SerializedName
import com.ma7moud27.shelfy.model.work.Description

data class Book(
    var key: String? = null,
    val title: String? = null,
    val description: Description? = Description(),

    val contributors: List<Contributor>? = null,
    val covers: List<Int>? = null,

    @SerializedName("edition_name") val editionName: String? = null,
    @SerializedName("isbn_10") val isbn10: List<String>? = null,
    @SerializedName("isbn_13") val isbn13: List<String>? = null,

    val languages: List<Language>? = null,
    val notes: Notes? = Notes(),
    @SerializedName("number_of_pages") val numberOfPages: Int? = null,
    @SerializedName("physical_format") val physicalFormat: String? = null,
    @SerializedName("publish_date") val publishDate: String? = null,
    val publishers: List<String>? = null,
    val series: List<String>? = null,
    @SerializedName("table_of_contents") val tableOfContents: List<TableOfContent>? = null,
)
