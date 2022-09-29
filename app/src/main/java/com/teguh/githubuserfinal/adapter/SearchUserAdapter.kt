package com.teguh.githubuserfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.databinding.CardviewUserBinding
import com.teguh.githubuserfinal.network.SearchUserItems

class SearchUserAdapter( private val showDetail: (String) -> Unit) : ListAdapter<SearchUserItems, SearchUserAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserAdapter.ViewHolder {
        return ViewHolder(CardviewUserBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: SearchUserAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private var binding: CardviewUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchUserItems: SearchUserItems) {
            binding.itemCardViewUser = searchUserItems

            binding.ivUserPicture.setOnClickListener {
                showDetail(searchUserItems.login)
            }

            itemView.setOnClickListener {
                showDetail(searchUserItems.login)
            }

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<SearchUserItems>(){
        override fun areItemsTheSame(oldItem: SearchUserItems, newItem: SearchUserItems): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SearchUserItems, newItem: SearchUserItems): Boolean {
            return oldItem.id == newItem.id
        }
    }
}