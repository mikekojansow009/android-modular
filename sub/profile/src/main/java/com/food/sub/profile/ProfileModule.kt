package com.food.sub.profile

import com.food.core.repository.RepositoryModule
import com.food.core.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 12/08/20.
 * Senior Android Developer
 */
@Module(includes = [RepositoryModule::class])
class ProfileModule(private val fragment: ProfileFragment) {

    @Provides
    fun getPresenter(userRepository: UserRepository): ProfilePresenter {
        return ProfilePresenter(fragment, userRepository)
    }
}