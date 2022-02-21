package com.example.hanwha_kimdahye.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hanwha_kimdahye.data.database.BookmarkDatabase
import com.example.hanwha_kimdahye.data.model.Docs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created By kimdahyee
 * on 02월 20일, 2020
 */

class BookmarkViewModel : ViewModel() {
    private val _bookmarks = MutableLiveData<List<Docs>>()
    val bookmarks: LiveData<List<Docs>>
        get() = _bookmarks

    fun getBookmarkNewsResult(context: Context, category: String) {
        val db = BookmarkDatabase.getInstance(context)
        CoroutineScope(Dispatchers.IO).launch {
            _bookmarks.postValue(db!!.bookmarkDao().select(category))
        }
    }
}
