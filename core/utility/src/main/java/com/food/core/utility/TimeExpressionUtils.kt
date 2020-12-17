package com.food.core.utility

import android.content.Context
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
object TimeExpressionUtils {

    fun getGreetingMessageId(): Int {
        val c = Calendar.getInstance()

        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> R.string.good_morning
            in 12..15 -> R.string.good_afternoon
            in 16..20 -> R.string.good_evening
            in 21..23 -> R.string.good_night
            else -> R.string.hello
        }
    }

    fun getTimeFromSeconds(duration: Int, context: Context): String {
        val minutes = TimeUnit.SECONDS.toMinutes(duration.toLong())
        val hours = TimeUnit.SECONDS.toHours(duration.toLong())

        if (minutes < 60) return context.resources.getString(R.string.format_minutes, minutes)

        return if (hours > 1) context.resources.getString(R.string.format_hours, hours)
        else context.resources.getString(R.string.format_hour, hours)
    }

}