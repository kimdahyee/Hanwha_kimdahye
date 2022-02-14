package com.example.hanwha_kimdahye.data.remote.response

import com.example.hanwha_kimdahye.data.model.Docs
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val found: Boolean,
    val data: Data
) {
    data class Data(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        val docs: List<Docs>
    )
}
