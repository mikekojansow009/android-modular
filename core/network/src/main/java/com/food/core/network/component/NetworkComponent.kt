package com.food.core.network.component

import com.food.core.network.module.NetworkModule
import dagger.Component

/**
 * Created by mikekojansow on 29/07/20.
 * Senior Android Developer
 */
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun inject(any: Any)

}