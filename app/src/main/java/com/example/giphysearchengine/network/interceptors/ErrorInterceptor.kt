/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.network.interceptors

import com.example.giphysearchengine.network.errors.ApiException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            throw when (response.code) {
                400 -> ApiException.BadRequest
                403 -> ApiException.Forbidden
                404 -> ApiException.NotFound
                429 -> ApiException.TooManyRequest
                in 500..599 -> ApiException.ServerError("Server Error")
                else -> ApiException.ClientError("Unknown Error")
            }
        }
        return response
    }

}