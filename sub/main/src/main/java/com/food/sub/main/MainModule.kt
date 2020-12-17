package com.food.sub.main

import com.food.core.network.module.NetworkModule
import com.food.core.repository.RepositoryModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
@Module(includes = [NetworkModule::class, RepositoryModule::class])
class MainModule(private val mainFragment: MainFragment) {

    @Provides
    fun getPresenter(repository: MainRepository): MainPresenter {
        return MainPresenter(mainFragment, repository)
    }

    @Provides
    fun getRepository(retrofit: Retrofit): MainRepository {
        return MainRepository(retrofit)
    }

}