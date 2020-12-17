package com.food.core.utility

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mikekojansow on 19/11/20.
 * Senior Android Developer
 */
class CustomLinearSnapHelper: LinearSnapHelper() {

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {

        return super.findSnapView(layoutManager)
    }

//    private fun getStartView(layoutManager: RecyclerView.LayoutManager,
//                             helper: OrientationHelper): View? {
//        if (layoutManager)
//    }
}