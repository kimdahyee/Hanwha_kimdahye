package com.example.hanwha_kimdahye.data.remote

import com.example.hanwha_kimdahye.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeepSearchApiService {
    /*
    * clustering: 문서 클러스터링 사용 시 1
    * count: 한 페이지에 표시할 문서의 개수
    * page: 페이지 번호
    * */

    @GET("haystack/v1/news/economy/_search")
    suspend fun requestNewsSearch(
        @Query("query", encoded = true) query: String,
        @Query("clustering") clustering: Int,
        @Query("count") count: Int,
        @Query("page") page: Int
    ): Response<SearchResponse>

    @GET("haystack/v1/company/ir/_search")
    suspend fun requestIRSearch(
        @Query("query", encoded = true) query: String,
        @Query("clustering") clustering: Int,
        @Query("count") count: Int,
        @Query("page") page: Int
    ): Response<SearchResponse>

    @GET("haystack/v1/company/disclosure/_search")
    suspend fun requestDisclosureSearch(
        @Query("query", encoded = true) query: String,
        @Query("clustering") clustering: Int,
        @Query("count") count: Int,
        @Query("page") page: Int
    ): Response<SearchResponse>
}
