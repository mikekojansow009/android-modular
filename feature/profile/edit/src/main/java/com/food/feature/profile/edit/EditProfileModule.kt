package com.food.feature.profile.edit

import com.food.core.repository.RepositoryModule
import com.food.core.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by mikekojansow on 22/08/20.
 * Senior Android Developer
 */
@Module(includes = [RepositoryModule::class])
class EditProfileModule @Inject constructor(private val activity: EditProfileActivity) {

    @Provides
    fun getPresenter(userRepository: UserRepository): EditProfilePresenter{
        return EditProfilePresenter(activity, userRepository)
    }

}