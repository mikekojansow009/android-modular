package com.food.sub.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.core.base.fragment.BaseFragment
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.utility.Actions
import com.food.core.utility.CustomAnimator
import com.food.core.utility.EndlessScrollViewListener
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import kotlinx.android.synthetic.main.favorite_empty_view.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class FavoriteFragment(private val onFindRecipeClicked: () -> Unit) : BaseFragment(R.layout.fragment_favorite) {

    companion object {
        private const val MAX_COUNT_ITEM_GRID = 2
        private const val MAX_ANIMATION_TIME = 1500L

        private const val TAG_RECIPE_DETAIL_DIALOG = "RECIPE_DETAIL_DIALOG"
    }

    @Inject lateinit var presenter: FavoritePresenter

    private var hasFetchFavoriteRecipes = false

    private var endlessScroll: EndlessScrollViewListener? = null

    private var currentPage = 1

    private var hasInitiate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitiate) {
            DaggerFavoriteComponent.builder().favoriteModule(FavoriteModule(this))
                .contextModule(ContextModule(context!!)).build().inject(this)
        }

        presenter.fetchFavoriteRecipes(currentPage, {})

        setupSubviews()

        hasInitiate = true
    }

    private fun setupSubviews() {
        if (context == null) return

        setupRecyclerview()
        setupSwipeRefresh()
        setupFindButtonClicked()
    }

    private fun setupSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            presenter.fetchFavoriteRecipes(1, {})
        }
    }

    private fun setupRecyclerview() {
        val currentLayoutManager = GridLayoutManager(context, MAX_COUNT_ITEM_GRID)

        endlessScroll = object: EndlessScrollViewListener(currentLayoutManager, currentPage = currentPage, visibleThreshold = 3) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView?,
                context: Context
            ) {
                currentPage = page
                presenter.fetchFavoriteRecipes(page, onFinished = {
                    context.notifyLoadingEnded()
                })
            }
        }

        recyclerview.apply {
            layoutManager = currentLayoutManager
            adapter = FavoriteAdapter(presenter.currentRecipes, ::onRecipeClicked)
            itemAnimator = FavoriteItemAnimator()
            removeOnScrollListener(endlessScroll!!)
            addOnScrollListener(endlessScroll!!)
        }
    }

    private fun setupFindButtonClicked() {
        btn_find.setClickListener {
            onFindRecipeClicked()
        }
    }

    private fun onRecipeClicked(recipe: Recipe) {
        if (activity == null) return

        Actions.navigateToRecipeDetail(activity!!, recipe)
    }

    fun refreshRecipes(position: Int) {
        hasFetchFavoriteRecipes = true

        recyclerview?.adapter?.notifyItemInserted(position)
    }

    fun showLoadingRecipes() {
        loading.playLoading()
        CustomAnimator.startDefaultAnimation(0f, 1f) { alphaValue ->
            loading.alpha = alphaValue as Float
        }
    }

    fun hideLoadingRecipes() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(MAX_ANIMATION_TIME)

            loading.stopLoading()
            swipe_refresh.isRefreshing = false
        }

        CustomAnimator.startDefaultAnimation(1f, 0f, MAX_ANIMATION_TIME) { alphaValue ->
            loading.alpha = alphaValue as Float
        }
    }

    fun showEmptyView() {
        CustomAnimator.startDefaultAnimation(layout_empty.alpha, 1f, MAX_ANIMATION_TIME) { alphaValue ->
            layout_empty.alpha = alphaValue as Float
        }
    }

    fun hideEmptyView() {
        CustomAnimator.startDefaultAnimation(layout_empty.alpha, 0f, MAX_ANIMATION_TIME) { alphaValue ->
            layout_empty.alpha = alphaValue as Float
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteChange(event: FavoriteRecipeChangeEvent) {
        presenter.onRecipeChangeEvent(event.recipe)
    }
}