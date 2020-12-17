package com.food.core.base.activity

import android.os.Bundle
import com.food.core.broadcast.event.TokenExpiredEvent
import com.food.core.utility.Actions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by mikekojansow on 25/07/20.
 * Senior Android Developer
 */
open class AuthActivity(private val layoutResourceId: Int): BaseActivity(layoutResourceId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)

        super.onDestroy()
    }

    @Subscribe
    fun onTokenExpired(event: TokenExpiredEvent) {
        Actions.navigateToLoginPage(this)
    }
}