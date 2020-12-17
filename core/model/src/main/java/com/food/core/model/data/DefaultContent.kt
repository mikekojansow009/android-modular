package com.food.core.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by mikekojansow on 17/09/20.
 * Senior Android Developer
 */
@Parcelize
data class DefaultContent(val title: String, val contents: ArrayList<String>): Parcelable