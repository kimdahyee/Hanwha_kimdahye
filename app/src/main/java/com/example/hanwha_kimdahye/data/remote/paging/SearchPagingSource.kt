package com.example.hanwha_kimdahye.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.data.remote.DeepSearchApiService
import java.io.IOException

const val STARTING_PAGE_INDEX = 1
const val CLUSTERING_INDEX = 1
const val COUNT = 10

/*
* 데이터 소스와 이 소스에서 데이터를 검색하는 방법 정리
* */
class SearchPagingSource(
    private val index: Int,
    private val query: String,
    private val service: DeepSearchApiService
) : PagingSource<Int, Docs>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Docs> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = when (index) {
                1 -> service.requestNewsSearch(query, CLUSTERING_INDEX, COUNT, position)
                2 -> service.requestIRSearch(query, CLUSTERING_INDEX, COUNT, position)
                else -> service.requestDisclosureSearch(query, CLUSTERING_INDEX, COUNT, position)
            }
            val items = response.body()!!.data.docs
            LoadResult.Page(
                data = items,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (items.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Docs>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
