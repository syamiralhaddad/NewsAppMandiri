package com.rakamin.mandirinews.data

data class News(
    val source: Source,
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: String
)