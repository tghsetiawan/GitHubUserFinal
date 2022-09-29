package com.teguh.githubuserfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.databinding.CardviewUserFollowerBinding
import com.teguh.githubuserfinal.network.UserFollower

class FollowerAdapter : ListAdapter<UserFollower, FollowerAdapter.ViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardviewUserFollowerBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private var binding: CardviewUserFollowerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: UserFollower) {
            binding.itemCardviewFollower = follower
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<UserFollower>(){
        override fun areItemsTheSame(oldItem: UserFollower, newItem: UserFollower): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserFollower, newItem: UserFollower): Boolean {
            return oldItem.id == newItem.id
        }
    }
}