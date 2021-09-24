/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.network

import javax.inject.Qualifier

/**
 * We need this annotation (qualifiers) to
 * have multiple binding for same type. Since both APIKeyInterceptor and ErrorInterceptor
 * have same type (Interceptor), we need qualifiers to differentiate them
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ErrorInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LanguageInterceptorOkHttpClient