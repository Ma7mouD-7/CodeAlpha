package com.ma7moud27.shelfy.model.work

import com.google.gson.annotations.SerializedName
import com.ma7moud27.shelfy.model.BookShelf
import com.ma7moud27.shelfy.model.book.Book
import com.ma7moud27.shelfy.model.rating.Rating

data class Work(
    val key: String? = null,

    val title: String? = null,
    val description: Description? = Description(),
    val links: List<Link>? = null,

    @SerializedName("subject_people")
    val people: List<String>? = null,
    @SerializedName("subject_places")
    val places: List<String>? = null,
    val subjects: List<String>? = null,

    val covers: List<Int>? = null,

    val excerpts: List<Excerpt>? = null,
    @SerializedName("first_publish_date")
    val firstPublishDate: String? = null,

    @SerializedName("authors")
    val authorsList: List<AuthorItem>? = null,

    var ratings: Rating? = Rating(),
    val bookShelf: BookShelf? = BookShelf(),
    var book: Book? = Book(),
    var author: List<String>? = null,
)

data class AuthorItem(
    val author: AuthorData? = null,
)

data class AuthorData(
    val key: String? = null,
)

/*
"covers": [],
"first_publish_date": "2003",

"authors": [],
"excerpts": [],

 */
