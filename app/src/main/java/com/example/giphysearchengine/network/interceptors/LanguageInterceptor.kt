/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.network.interceptors

import android.provider.SyncStateContract
import com.example.giphysearchengine.BuildConfig
import com.example.giphysearchengine.utils.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Interceptor add API Key as Query param for every request
 */
class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val httpUrl: HttpUrl = original.url
        val url: HttpUrl = httpUrl.newBuilder()
            .addQueryParameter("lang", Constants.lang)
            .build()
        val request: Request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}