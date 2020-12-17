package com.food.sub.main

import com.food.core.network.NetworkResponse
import com.food.core.network.Response
import com.food.sub.main.model.HomeRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class MainRepository @Inject constructor(private val retrofit: Retrofit) {

    private val service = retrofit.create(MainService::class.java)

    fun getHomeRecipe(onSuccess: (ArrayList<HomeRecipe>) -> Unit) {
        GlobalScope.async(context = Dispatchers.IO) {
            println("Try to get data..")
            when(val responseHomeRecipe = service.homeRecipes()) {
                is NetworkResponse.Success -> {
                    val data = responseHomeRecipe.body.data

                    withContext(Dispatchers.Main) {
                        data?.let(onSuccess)
                        println("Success load data")
                    }
                }
                else -> {println("Something wrong")}
            }
        }
    }

    private interface MainService {
        @GET("food/home")
        suspend fun homeRecipes(): NetworkResponse<Response.Success<ArrayList<HomeRecipe>>, Response.Error<Nothing>>
    }

}