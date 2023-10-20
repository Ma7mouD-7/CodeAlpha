package com.ma7moud27.shelfy.model.rating

import com.google.gson.annotations.SerializedName

data class RatingCounts(
    @SerializedName("1") val oneStarCount: Int? = null,
    @SerializedName("2") val twoStarCount: Int? = null,
    @SerializedName("3") val threeStarCount: Int? = null,
    @SerializedName("4") val fourStarCount: Int? = null,
    @SerializedName("5") val fiveStarCount: Int? = null,
)
