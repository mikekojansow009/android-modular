package com.food.sub.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mikekojansow on 03/10/20.
 * Senior Android Developer
 */
class MainRecipesSpaces: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val paddingItems = view.resources.getDimensionPixelSize(R.dimen.space_between_items)

        outRect.bottom = 0

        if (!isFirstItem(parent, view)) {
            outRect.top = paddingItems
            outRect.bottom = paddingItems
        }

        if (isLastItem(parent, view)) outRect.bottom = 0
    }

    private fun isFirstItem(recyclerView: RecyclerView, view: View): Boolean {
        val childPosition = recyclerView.getChildAdapterPosition(view)

        return childPosition == 0 || childPosition == 1
    }

    private fun isLastItem(recyclerView: RecyclerView, view: View): Boolean {
        val totalItemCount = (recyclerView.adapter?.itemCount ?: 0) - 1

        return recyclerView.getChildAdapterPosition(view) == totalItemCount
    }
}