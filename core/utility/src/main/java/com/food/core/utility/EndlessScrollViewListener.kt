package com.food.core.utility

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
abstract class EndlessScrollViewListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val visibleThreshold: Int = 5,
    private var currentPage: Int = 1
): RecyclerView.OnScrollListener() {

    private var previousItemCount = 0
    private var isLoading = true
    private var startingPageIndex = 0

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0

        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[1] > maxSize) {
                maxSize = lastVisibleItemPositions[1]
            }
        }

        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        }

        if (totalItemCount < previousItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.isLoading = true
            }
        }

        if (isLoading && totalItemCount > previousItemCount) {
            isLoading = false
            previousItemCount = totalItemCount
        }

        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            isLoading = true

            val context = Context(this)
            onLoadMore(currentPage, totalItemCount, recyclerView, context)
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?, context: Context)

    fun resetState() {
        currentPage = startingPageIndex
        previousItemCount = 0
        isLoading = true
    }

    class Context(private val source: EndlessScrollViewListener) {
        fun notifyLoadingEnded() {
            source.isLoading = false
        }
    }
}