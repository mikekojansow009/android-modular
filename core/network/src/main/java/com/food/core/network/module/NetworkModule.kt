package com.food.core.network.module

import com.food.core.network.NetworkCallAdapterFactory
import com.food.core.network.NetworkInfo
import com.food.core.network.intercept.AuthIntercept
import com.food.core.utility.BaseInfo
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by mikekojansow on 28/07/20.
 * Senior Android Developer
 */
@Module
open class NetworkModule {

    @Provides
    fun getRetrofit(): Retrofit {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(AuthIntercept())
            .connectTimeout(NetworkInfo.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkInfo.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkInfo.TIMEOUT, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BaseInfo.baseUrl)
            .client(client)
            .addCallAdapterFactory(NetworkCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}