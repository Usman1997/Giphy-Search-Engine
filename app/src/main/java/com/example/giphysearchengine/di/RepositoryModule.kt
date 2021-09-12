/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.di

import com.example.giphysearchengine.network.GiphyService
import com.example.giphysearchengine.repository.SearchRepository
import com.example.giphysearchengine.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(
        giphyService: GiphyService
    ): SearchRepository = SearchRepositoryImpl(giphyService)
}