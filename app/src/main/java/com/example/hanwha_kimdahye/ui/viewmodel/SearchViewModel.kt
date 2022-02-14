package com.example.hanwha_kimdahye.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.data.remote.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var index = 0
    val searchQuery = MutableLiveData("")

    fun handleIntent(intent: Intent?) {
        if (intent == null) return
        index = intent.getSerializableExtra("index") as Int
    }

    var searchResult: Flow<PagingData<Docs>>? = null

    fun requestNewsSearch(query: String): Flow<PagingData<Docs>> {
        val lastResult = searchResult
        if (searchQuery.value.toString() == query && lastResult != null) {
            return lastResult
        }
        return searchRepository.requestSearch(index, query)
            .cachedIn(viewModelScope)
    }

    private var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
