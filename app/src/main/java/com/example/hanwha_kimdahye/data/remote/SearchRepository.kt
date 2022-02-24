package com.example.hanwha_kimdahye.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.data.remote.paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: DeepSearchApiService
) {
    fun requestSearch(index: Int, query: String): Flow<PagingData<Docs>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SearchPagingSource(index, query, service)
        }
    ).flow
}
