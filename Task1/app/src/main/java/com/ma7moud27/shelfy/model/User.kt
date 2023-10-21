package com.ma7moud27.shelfy.model

data class User(
    val name: String = "",
    val email: String = "",
    val favoriteList: MutableList<SearchBookItem> = mutableListOf(),
    val currentlyReadList: MutableList<SearchBookItem> = mutableListOf(),
    val planToReadList: MutableList<SearchBookItem> = mutableListOf(),
    val doneReadingList: MutableList<SearchBookItem> = mutableListOf(),
)
