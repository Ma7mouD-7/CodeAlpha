package com.ma7moud27.onlinebookshop.model.rating

import com.google.gson.annotations.SerializedName

data class RatingCounts(
    @SerializedName("1")
    val oneStarCount: Int = 0,
    @SerializedName("2")
    val twoStarCount: Int = 0,
    @SerializedName("3")
    val threeStarCount: Int = 0,
    @SerializedName("4")
    val fourStarCount: Int = 0,
    @SerializedName("5")
    val fiveStarCount: Int = 0
)