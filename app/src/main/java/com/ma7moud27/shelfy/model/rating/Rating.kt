package com.ma7moud27.shelfy.model.rating

data class Rating(
    val counts: RatingCounts? = RatingCounts(),
    val summary: RatingSummary? = RatingSummary(),
)
