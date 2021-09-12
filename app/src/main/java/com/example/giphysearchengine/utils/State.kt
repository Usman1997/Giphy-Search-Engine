/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.utils

sealed class State<T>(
    open val data: T?
) {
    class Idle<T>(override val data: T? = null) : State<T>(data)
    class Error<T>(override val data: T? = null, val error: Throwable) : State<T>(data)
    class Loading<T>(override val data: T? = null) : State<T>(data)

    val isLoading: Boolean
        get() = this is Loading

    val isIdle: Boolean
        get() = this is Idle

    val isError: Boolean
        get() = this is Error

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