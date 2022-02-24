package com.example.hanwha_kimdahye.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.data.remote.paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
* Paging의 viewModel layer
* PagingSource 객체 및 PagingConfig 객체를 바탕으로 반응형 스트림 생성
* Pager로 부터 Flow로 변환
* */
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
