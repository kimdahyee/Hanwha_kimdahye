package com.example.hanwha_kimdahye.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DeepSearchApiServiceImpl {

    private const val BASE_URL = "https://api.ddi.deepsearch.com/"
    private const val token =
        "Basic MGQ1NzdiN2U0YmRmNGE2ZWE0ZWMxZGIxNmE0NDM4Y2M6ZTgzYTAzZGVhMmNlZmUzNzA4MjExMTZmMGRjNzkxMzg1NDU5YWNhZTIyNzQzNTUxNWUzNDY0NTFhZTViYjdiMQ=="

    fun getClient(): DeepSearchApiService {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val interceptor = Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
                proceed(newRequest)
            }
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DeepSearchApiService::class.java)
    }
}
