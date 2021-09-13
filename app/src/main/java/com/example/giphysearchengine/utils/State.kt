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
    open val data: T?
) {
    class Idle<T>(override val data: T? = null) : State<T>(data)
    class Error<T>(override val data: T? = null, val error: Throwable) : State<T>(data)
    class Loading<T>(override val data: T? = null) : State<T>(data)

    companion object {
        fun <T> loading(): State<T> {
            return Loading()
        }

        fun <T> idle(data: T?): State<T> {
            return Idle(data)
        }

        fun <T> error(error: Throwable): State<T> {
            return Error(null, error)
        }
    }

}