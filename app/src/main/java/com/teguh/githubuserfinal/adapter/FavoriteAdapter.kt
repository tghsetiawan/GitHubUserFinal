package com.teguh.githubuserfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.database.User
import com.teguh.githubuserfinal.databinding.CardviewUserFavoriteBinding
import com.teguh.githubuserfinal.viewmodel.FavoriteViewModel

class FavoriteAdapter constructor(private val viewModel:FavoriteViewModel, private val showDetailListener: (String) -> Unit) : ListAdapter<User, FavoriteAdapter.UserViewHolder>(USERS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.UserViewHolder {
        val binding = CardviewUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.UserViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
        holder.deleteBtn.setOnClickListener {
            Toast.makeText(holder.deleteBtn.context, "Berhasil Hapus!", Toast.LENGTH_SHORT).show()
            viewModel.delete(item)
            notifyItemRemoved(position)
        }
    }

    inner class UserViewHolder(private val binding: CardviewUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        val deleteBtn = binding.ivDelete
        fun bind(user: User) {
            binding.itemCardViewFavoriteUser = user

            binding.ivUserPicture.setOnClickListener {
                showDetailListener(user.githubLogin.toString())
            }

            itemView.setOnClickListener {
                showDetailListener(user.githubLogin.toString())
            }
            
            binding.executePendingBindings()
        }
    }

    companion object {
        private val USERS_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.githubLogin == newItem.githubLogin
            }
        }
    }
}