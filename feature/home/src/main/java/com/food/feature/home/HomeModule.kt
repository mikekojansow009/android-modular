package com.food.feature.home

import com.food.core.repository.RepositoryModule
import com.food.core.repository.preference.UserPreferencesRepository
import com.food.core.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 08/08/20.
 * Senior Android Developer
 */
@Module(includes = [RepositoryModule::class])
class HomeModule(private val homeActivity: HomeActivity) {

    @Provides
    fun getPresenter(userRepository: UserRepository, userPreferencesRepository: UserPreferencesRepository): HomePresenter {
        return HomePresenter(homeActivity, userRepository, userPreferencesRepository)
    }

}