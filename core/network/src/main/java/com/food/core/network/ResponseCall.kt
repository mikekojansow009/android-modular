package com.food.core.network

import com.food.core.broadcast.event.TokenExpiredEvent
import com.food.core.broadcast.event.UpgradeRequiredEvent
import com.food.core.exception.ServerException
import com.food.core.utility.Log
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.lang.UnsupportedOperationException

/**
 * Created by mikekojansow on 29/07/20.
 * Senior Android Developer
 */
internal class ResponseCall<T: Any, E: Any>(
    private val delegate: Call<T>,
    private val errorConverter: Converter<ResponseBody, E>
): Call<NetworkResponse<T, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<T, E>>) {
        return delegate.enqueue(object: Callback<T> {
            override fun onFailure(call: Call<T>, throwable: Throwable) {
                Log.printLog("Enqueue Error", "Error Response call : ${throwable.message}", Exception(throwable.message))
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.UnknownError(throwable)
                    else -> NetworkResponse.UnknownError(throwable)
                }
                callback.onResponse(this@ResponseCall, Response.success(networkResponse))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(this@ResponseCall, Response.success(NetworkResponse.Success(body)))
                    } else {
                        callback.onResponse(this@ResponseCall, Response.success(NetworkResponse.UnknownError()))
                    }
                } else {
                    val errorObject = JSONObject(error?.string() ?: "")

                    when (response.code()) {
                        NetworkInfo.UNAUTHORIZE -> EventBus.getDefault().post(TokenExpiredEvent())
                        NetworkInfo.UPGRADE_REQUIRED -> EventBus.getDefault().post((UpgradeRequiredEvent()))
                        NetworkInfo.SERVER_ERROR -> callback.onResponse(this@ResponseCall, Response.success(NetworkResponse.UnknownError(ServerException())))
                        else -> {
                            val errorBody = when {
                                error != null -> try { errorConverter.convert(error) } catch(ex: Exception) { null }
                                else -> null
                            }
                            errorBody?.let {
                                callback.onResponse(this@ResponseCall, Response.success(NetworkResponse.ApiError(errorBody)))
                            } ?: Response.success(NetworkResponse.UnknownError(ServerException()))
                        }
                    }
                }
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone(): Call<NetworkResponse<T, E>> = ResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<T, E>> {
        throw UnsupportedOperationException("Doesn't support execute method!")
    }

    override fun request(): Request = delegate.request()
}