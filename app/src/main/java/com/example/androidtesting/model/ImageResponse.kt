package com.example.androidtesting.model

data class ImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageHitsResponse>
)
