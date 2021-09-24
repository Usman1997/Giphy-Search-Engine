/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.utils

/**
 * Sealed class state to manage different state of the request we make to server. We have 3
 * different states. Loading -> when the data is loading, Error -> When we get error from
 * server. Idle -> when the response is successful.
 */
sealed class State<T>(
    val data: T? = null
) {
    class Success<T>(data: T? = null) : State<T>(data)
    class Error<T>(val error: Throwable) : State<T>()
    class Loading<T> : State<T>()

    companion object {
        fun <T> loading(): State<T> {
            return Loading()
        }

        fun <T> success(data: T?): State<T> {
            return Success(data)
        }

        fun <T> error(error: Throwable): State<T> {
            return Error(error)
        }
    }

}