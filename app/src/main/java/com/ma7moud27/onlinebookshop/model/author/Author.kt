package com.ma7moud27.onlinebookshop.model.author

import com.google.gson.annotations.SerializedName
import com.ma7moud27.onlinebookshop.model.work.Link
import com.ma7moud27.onlinebookshop.utils.Constants

data class Author(
    val key: String = "",
    val name: String = "",
    @SerializedName("fuller_name")
    val fullName: String = "",
    @SerializedName("personal_name")
    val personalName: String = "",
    @SerializedName("alternate_names")
    val alternateNames: List<String> = listOf(),
    val bio: Bio = Bio(),
    val photos: List<Int> = listOf(),
    @SerializedName("birth_date")
    val birthDate: String = "",
    @SerializedName("death_date")
    val deathDate: String = "",
    val title: String = "",
    val wikipedia: String = "",
    val links: List<Link> = listOf(),
)

