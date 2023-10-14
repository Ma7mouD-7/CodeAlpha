package com.ma7moud27.onlinebookshop.model

import com.google.gson.annotations.SerializedName

data class SearchAuthorItem(
    @SerializedName("birth_date")
    val birthDate: String = "",
    val key: String = "",
    val name: String = "",
    @SerializedName("top_work")
    val topWork: String = "",
    @SerializedName("work_count")
    val workCount: Int = 0
)