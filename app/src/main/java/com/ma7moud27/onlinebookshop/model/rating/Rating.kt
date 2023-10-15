package com.ma7moud27.onlinebookshop.model.rating

data class Rating(
    val counts: RatingCounts? = RatingCounts(),
    val summary: RatingSummary? = RatingSummary(),
)
