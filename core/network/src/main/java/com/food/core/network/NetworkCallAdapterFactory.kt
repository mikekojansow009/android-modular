package com.food.core.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by mikekojansow on 29/07/20.
 * Senior Android Developer
 */
internal class NetworkCallAdapterFactory: CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (NetworkResponse::class.java != getRawType(responseType)) return null

        check(responseType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        val errorBodyType = getParameterUpperBound(1, responseType)

        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return NetworkResponseAdapter<Any, Any>(successBodyType, errorBodyConverter)
    }
}