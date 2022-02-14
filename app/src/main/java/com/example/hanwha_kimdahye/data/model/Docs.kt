package com.example.hanwha_kimdahye.data.model

import com.google.gson.annotations.SerializedName

data class Docs(
    val category: String,
    val section: String,
    val publisher: String,
    val title: String,
    val content: String,
    @SerializedName("image_urls")
    val imageUrls: List<String?>,
    @SerializedName("content_url")
    val contentUrl: String
)
