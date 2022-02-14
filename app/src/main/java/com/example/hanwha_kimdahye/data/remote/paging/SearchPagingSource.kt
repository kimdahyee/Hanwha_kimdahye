package com.example.hanwha_kimdahye.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.data.remote.DeepSearchApiService
import com.example.hanwha_kimdahye.data.remote.response.SearchResponse
import retrofit2.Response
import java.io.IOException

const val STARTING_PAGE_INDEX = 1
const val CLUSTERING_INDEX = 1
const val COUNT = 10

class SearchPagingSource(
    private val index: Int,
    private val query: String,
    private val service: DeepSearchApiService
) : PagingSource<Int, Docs>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Docs> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            lateinit var response: Response<SearchResponse>
            when (index) {
                1 ->
                    response =
                        service.requestNewsSearch(query, CLUSTERING_INDEX, COUNT, position)
                2 ->
                    response =
                        service.requestIRSearch(query, CLUSTERING_INDEX, COUNT, position)
                3 ->
                    response =
                        service.requestDisclosureSearch(query, CLUSTERING_INDEX, COUNT, position)
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
