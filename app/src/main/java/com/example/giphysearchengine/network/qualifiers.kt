package com.example.giphysearchengine.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ErrorInterceptorOkHttpClient