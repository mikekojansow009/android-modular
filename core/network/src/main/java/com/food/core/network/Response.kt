package com.food.core.network

/**
 * Created by mikekojansow on 28/07/20.
 * Senior Android Developer
 */
class Response {
    data class Success<T>(
        var data: T? = null,
        var message: String? = null,
        var time: String
    )

    data class Error<E>(
        var errors: E? = null,
        var message: String
    )
}