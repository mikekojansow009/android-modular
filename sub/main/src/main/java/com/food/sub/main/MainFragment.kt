package com.food.sub.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.core.base.fragment.BaseFragment
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.repository.user.UserRepository
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import kotlinx.android.synthetic.main.fragment_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class MainFragment : BaseFragment(R.layout.fragment_main) {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var mainAdapter: MainAdapter

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
            DaggerMainComponent.builder().contextModule(ContextModule(context!!))
                .mainModule(MainModule(this)).build().inject(this)
        }

        setRecyclerview()

        presenter.loadHomeRecipes()

        hasInitiate = true
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun setRecyclerview() {
        mainAdapter = MainAdapter(presenter.currentHomeRecipes, childFragmentManager, userRepository)

        main_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = mainAdapter

            addItemDecoration(MainRecipesSpaces())
        }
    }

    fun refreshData() {
        mainAdapter.notifyDataSetChanged()
    }

    fun refreshHome(start: Int, end: Int) {
        mainAdapter.notifyItemRangeInserted(start, end)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRecipeFavoriteChange(event: FavoriteRecipeChangeEvent) {
        presenter.reloadHomeRecipes(event.recipe)
    }

}