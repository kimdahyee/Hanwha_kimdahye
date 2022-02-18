package com.example.hanwha_kimdahye.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    @PrimaryKey
    val uid: Long,
    val category: String,
    val section: String,
    val publisher: String,
    val author: String,
    val title: String,
    val content: String,
    val imageUrl: String?,
    val contentUrl: String
)
