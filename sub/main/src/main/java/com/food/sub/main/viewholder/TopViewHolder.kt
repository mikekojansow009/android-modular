package com.food.sub.main.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.food.core.firebase.crashlytic.CrashlyticConfig
import com.food.core.preferences.Pref
import com.food.core.repository.user.UserRepository
import com.food.core.utility.TimeExpressionUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.top_view_holder.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class TopViewHolder(override val containerView: View,
                    private val userRepository: UserRepository): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun set() {
        val greetingsResourceId = TimeExpressionUtils.getGreetingMessageId()

        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUser()
            val name = "${(user?.firstName) ?: ""}!"

            CrashlyticConfig.setUserId(user?.id ?: "", name)

            withContext(Dispatchers.Main) {
                tv_greetings.text =
                    "${containerView.context.resources.getString(greetingsResourceId)} $name"
            }
        }
    }
}