package com.ma7moud27.onlinebookshop.model.work

import com.google.gson.annotations.SerializedName
import com.ma7moud27.onlinebookshop.model.BookShelf
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.model.book.Book
import com.ma7moud27.onlinebookshop.model.rating.Rating

data class Work(
    val key: String = "",

    val title: String = "",
    val description: Description? = null,
    val links: List<Link> = listOf(),

    @SerializedName("subject_people")
    val people: List<String> = listOf(),
    @SerializedName("subject_places")
    val places: List<String> = listOf(),
    val subjects: List<String> = listOf(),

    val covers: List<Int> = listOf(),

    val excerpts: List<Excerpt> = listOf(),
    @SerializedName("first_publish_date")
    val firstPublishDate: String = "",

    @SerializedName("authors")
    val authorsList: List<AuthorItem> = listOf(),

    val ratings: Rating = Rating(),
    val bookShelf: BookShelf = BookShelf(),
    val book: Book = Book(),
    val author: Author = Author(),
)

data class AuthorItem(
    val author: AuthorData = AuthorData(),
)

data class AuthorData(
    val key: String = "",
)

/*
"covers": [],
"first_publish_date": "2003",

"authors": [],
"excerpts": [],

 */
