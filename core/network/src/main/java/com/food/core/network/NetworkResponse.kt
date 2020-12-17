package com.food.core.network

/**
 * Created by mikekojansow on 28/07/20.
 * Senior Android Developer
 */
sealed class NetworkResponse<out T: Any, out E: Any> {

    data class Success<T: Any>(val body: T): NetworkResponse<T, Nothing>()

    data class ApiError<E: Any>(val error: E): NetworkResponse<Nothing, E>()

    data class UnknownError(var any: Any? = null): NetworkResponse<Nothing, Nothing>()
}