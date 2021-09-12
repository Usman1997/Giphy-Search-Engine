/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.di

import com.example.giphysearchengine.BuildConfig
import com.example.giphysearchengine.network.ApiKeyInterceptorOkHttpClient
import com.example.giphysearchengine.network.ErrorInterceptorOkHttpClient
import com.example.giphysearchengine.network.GiphyService
import com.example.giphysearchengine.network.interceptors.ApiKeyInterceptor
import com.example.giphysearchengine.network.interceptors.ErrorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    @ApiKeyInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @ErrorInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesErrorInterceptor(): Interceptor = ErrorInterceptor()

    @Singleton
    @Provides
    fun providesOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApiKeyInterceptorOkHttpClient apiKeyInterceptor: Interceptor,
        @ErrorInterceptorOkHttpClient errorInterceptor: Interceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(errorInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesGiphyService(retrofit: Retrofit): GiphyService =
        retrofit.create(GiphyService::class.java)

}