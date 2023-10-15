package com.ma7moud27.onlinebookshop.model.author

import com.google.gson.annotations.SerializedName
import com.ma7moud27.onlinebookshop.model.work.Link
import com.ma7moud27.onlinebookshop.utils.Constants

data class Author(
    val key: String? = null,
    val name: String? = null,
    @SerializedName("fuller_name")
    val fullName: String? = null,
    @SerializedName("personal_name")
    val personalName: String? = null,
    @SerializedName("alternate_names")
    val alternateNames: List<String>? = null,
    val bio: Bio? = Bio(),
    val photos: List<Int>? = null,
    @SerializedName("birth_date")
    val birthDate: String? = null,
    @SerializedName("death_date")
    val deathDate: String? = null,
    val title: String? = null,
    val wikipedia: String? = null,
    val links: List<Link>? = null,
)

