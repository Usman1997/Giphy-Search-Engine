/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.network.errors

import android.content.res.Resources
import com.example.giphysearchengine.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toLocalizedMessage(resources: Resources) =
    /**
     * Here we check the type of Exception at run time and pass the message
     * accordingly to show meaningful information to the user
     */
    when (this) {
        is SocketTimeoutException -> resources.getString(R.string.error_socket_timeout_exception)
        is ConnectException -> resources.getString(R.string.error_unknown_host)
        is UnknownHostException -> resources.getString(R.string.error_unknown_host)
        is ApiException.Forbidden -> resources.getString(R.string.error_forbidden)
        is ApiException.NotFound -> resources.getString(R.string.error_not_found)
        is ApiException.BadRequest -> resources.getString(R.string.error_bad_request)
        is ApiException.TooManyRequest -> resources.getString(R.string.error_too_many_request)
        else -> message ?: "Unknown Error: $this"
    }.apply {
        printStackTrace()
    }