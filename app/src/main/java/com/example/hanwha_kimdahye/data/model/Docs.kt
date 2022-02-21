package com.example.hanwha_kimdahye.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Docs(
    @PrimaryKey
    val uid: Long,
    val category: String,
    val section: String,
    val publisher: String,
    val author: String,
    val title: String,
    val content: String,

    @SerializedName("image_urls")
    val imageUrls: List<String?>,

    @SerializedName("content_url")
    val contentUrl: String
)
