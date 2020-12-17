package com.food.sub.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class SearchRecipeSpaces: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val paddingItems = view.resources.getDimensionPixelSize(R.dimen.padding_items)

        if (isFirstItem(parent, view)) {
            outRect.bottom = paddingItems
        }

        if (!isFirstItem(parent, view)) {
            outRect.top = paddingItems
            outRect.bottom = paddingItems
        }

        if (isLastItem(parent, view)) outRect.bottom = 0
    }

    private fun isFirstItem(recyclerView: RecyclerView, view: View): Boolean {
        val childPosition = recyclerView.getChildAdapterPosition(view)

        return childPosition == 0
    }

    private fun isLastItem(recyclerView: RecyclerView, view: View): Boolean {
        val totalItemCount = (recyclerView.adapter?.itemCount ?: 0) - 1

        return recyclerView.getChildAdapterPosition(view) == totalItemCount
    }

}