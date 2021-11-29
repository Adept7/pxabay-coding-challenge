package com.shevel.pixabaychallenge.model

data class SearchImagesResponse(
    val hits: List<ImageData>,
    val total: Int,
    val totalHits: Int
)