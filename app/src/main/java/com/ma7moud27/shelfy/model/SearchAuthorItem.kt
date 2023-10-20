package com.ma7moud27.shelfy.model

import com.google.gson.annotations.SerializedName

data class SearchAuthorItem(
    @SerializedName("birth_date")
    val birthDate: String? = null,
    val key: String? = null,
    val name: String? = null,
    @SerializedName("top_work")
    val topWork: String? = null,
    @SerializedName("work_count")
    val workCount: Int? = null,
)
