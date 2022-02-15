package com.example.hanwha_kimdahye.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hanwha_kimdahye.data.model.Detail

class DetailViewModel : ViewModel() {

    private val _detail = MutableLiveData<Detail>()
    val detail: LiveData<Detail>
        get() = _detail

    fun handleIntent(intent: Intent) {
        val news = Detail(
            title = intent.getStringExtra("title")!!,
            publisher = intent.getStringExtra("publisher")!!,
            author = intent.getStringExtra("author")!!,
            imageUrl = intent.getStringExtra("imageUrl"),
            content = intent.getStringExtra("content")!!
        )
        _detail.value = news
    }
}
