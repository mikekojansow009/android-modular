package com.food.sub.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.food.core.repository.user.UserRepository
import com.food.sub.main.model.HomeRecipe
import com.food.sub.main.viewholder.ContentViewHolder
import com.food.sub.main.viewholder.TopViewHolder

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class MainAdapter(private var homeRecipes: ArrayList<HomeRecipe>,
                  private val fm: FragmentManager,
                  private val userRepository: UserRepository
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> TopViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.top_view_holder, parent, false), userRepository)
            else -> ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_holder, parent, false), fm)
        }
    }

    override fun getItemCount(): Int {
        return homeRecipes.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopViewHolder -> holder.set()
            is ContentViewHolder -> holder.setContent(homeRecipes[position - 1], position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return HEADER

        return position
    }
}