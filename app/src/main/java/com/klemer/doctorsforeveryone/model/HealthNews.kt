package com.klemer.doctorsforeveryone.model

data class HealthNewsResponse(
    val articles: List<HealthNews>
)

data class HealthNews(
    val title: String,
    val description: String,
    val urlToImage: String,
    val date: String,
    val url: String
)
