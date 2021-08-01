package com.defov.defovandroid.data.remote.adapter

import java.io.IOException

sealed class NetworkResponse<out S : Any, out E : Any> {
    /**
     * Success response with body
     */
    data class Success<S : Any>(val code: Int, val body: S) : NetworkResponse<S, Nothing>()

    /**
     * Failure response with body
     */
    data class Failure<E : Any>(val code: Int, val body: E) : NetworkResponse<Nothing, E>()

    /**
     * Network error
     */
    data class NetworkError(val code: Int, val error: IOException) : NetworkResponse<Nothing, Nothing>()

    data class UnknownError(val code: Int, val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}