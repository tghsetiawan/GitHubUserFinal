package com.teguh.githubuserfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.databinding.CardviewUserFollowingBinding
import com.teguh.githubuserfinal.network.UserFollowing

class FollowingAdapter : ListAdapter<UserFollowing, FollowingAdapter.ViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardviewUserFollowingBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private var binding: CardviewUserFollowingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(following: UserFollowing) {
            binding.itemCardviewFollowing = following
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<UserFollowing>(){
        override fun areItemsTheSame(oldItem: UserFollowing, newItem: UserFollowing): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserFollowing, newItem: UserFollowing): Boolean {
            return oldItem.id == newItem.id
        }
    }
}