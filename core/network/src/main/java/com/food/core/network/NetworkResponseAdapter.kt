package com.food.core.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * Created by mikekojansow on 28/07/20.
 * Senior Android Developer
 */
internal class NetworkResponseAdapter<T: Any, E: Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
): CallAdapter<T, Call<NetworkResponse<T, E>>> {

    override fun adapt(call: Call<T>): Call<NetworkResponse<T, E>> {
        return ResponseCall(call, errorBodyConverter)
    }

    override fun responseType(): Type = successType
}