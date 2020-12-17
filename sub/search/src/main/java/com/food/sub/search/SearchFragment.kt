package com.food.sub.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.core.base.fragment.BaseFragment
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.model.extension.onTextChanged
import com.food.core.utility.Actions
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import kotlinx.android.synthetic.main.fragment_search.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by mikekojansow on 25/09/20.
 * Senior Android Developer
 */
class SearchFragment: BaseFragment(R.layout.fragment_search) {

    @Inject lateinit var presenter: SearchPresenter

    private var currentPage = 1

    private var searchAdapter: SearchAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        DaggerSearchComponent.builder().searchModule(SearchModule(this)).contextModule(ContextModule(context!!)).build().inject(this)

        setupView()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun setupView() {
        setupRecyclerView()
        setupEditText()
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter(presenter.currentRecipes, onMarkAsFavorite = { recipe ->

        }, onRecipeClicked = { recipe ->
            navigateToRecipeDetail(recipe)
        })
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
            itemAnimator = SearchItemAnimator()
            addItemDecoration(SearchRecipeSpaces())
        }
    }

    private fun setupEditText() {
        et_search.onTextChanged(onAfterTextChanged = { text ->
            currentPage = 1
            presenter.searchRecipes(text, currentPage)
        })
    }

    private fun navigateToRecipeDetail(recipe: Recipe) {
        context?.let { Actions.navigateToRecipeDetail(it, recipe) }
    }

    fun refreshItems() {
        searchAdapter?.notifyDataSetChanged()
    }

    fun refreshItemChanges(position: Int) {
        searchAdapter?.notifyItemChanged(position)
    }

    fun refreshItemInserted(index: Int) {
        searchAdapter?.notifyItemInserted(index)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteChange(event: FavoriteRecipeChangeEvent) {
        presenter.onRecipeChangeEvent(event.recipe)
    }
}