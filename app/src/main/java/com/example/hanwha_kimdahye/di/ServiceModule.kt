package com.example.hanwha_kimdahye.di

import com.example.hanwha_kimdahye.data.remote.DeepSearchApiService
import com.example.hanwha_kimdahye.data.remote.DeepSearchApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideSearchService(): DeepSearchApiService =
        DeepSearchApiServiceImpl.getClient()
}
