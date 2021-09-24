/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.di

import com.example.giphysearchengine.BuildConfig
import com.example.giphysearchengine.network.*
import com.example.giphysearchengine.network.interceptors.ApiKeyInterceptor
import com.example.giphysearchengine.network.interceptors.ErrorInterceptor
import com.example.giphysearchengine.network.interceptors.LanguageInterceptor
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

    /**
     * Logging interceptor
     */
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    /**
     * Interceptor to inject API Key in query param for every request
     */
    @ApiKeyInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    /**
     * Interceptor to inject language in query param for every request
     */
    @LanguageInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesLanguageInterceptor(): Interceptor = LanguageInterceptor()

    /**
     * Error Interceptor for error handling. We need this annotation (qualifiers) to
     * have multiple binding for same type. Since both APIKeyInterceptor and ErrorInterceptor
     * have same type (Interceptor), we need qualifiers to differentiate them
     */
    @ErrorInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesErrorInterceptor(): Interceptor = ErrorInterceptor()

    /**
     * Provides Okhttp client to retrofit builder
     */
    @Singleton
    @Provides
    fun providesOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApiKeyInterceptorOkHttpClient apiKeyInterceptor: Interceptor,
        @ErrorInterceptorOkHttpClient errorInterceptor: Interceptor,
        @LanguageInterceptorOkHttpClient languageInterceptor: Interceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(languageInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    /**
     * Retrofit Builder
     */
    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    /**
     * Retrofit Service
     */
    @Singleton
    @Provides
    fun providesGiphyService(retrofit: Retrofit): GiphyService =
        retrofit.create(GiphyService::class.java)

}