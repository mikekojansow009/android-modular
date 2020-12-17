package com.food.feature.login

import com.food.core.auth.AuthManager
import com.food.core.network.module.NetworkModule
import com.food.core.repository.RepositoryModule
import com.food.core.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
@Module(includes = [NetworkModule::class, RepositoryModule::class])
class LoginModule(private val activity: LoginActivity) {

    @Provides
    fun getPresenter(repository: LoginRepository, userRepository: UserRepository, authManager: AuthManager): LoginPresenter {
        return LoginPresenter(activity, repository, userRepository, authManager)
    }

    @Provides
    fun getRepository(retrofit: Retrofit): LoginRepository {
        return LoginRepository(retrofit)
    }

    @Provides
    fun getAuthManager(): AuthManager {
        return AuthManager(activity)
    }
}