package com.teguh.githubuserfinal.viewmodel

import androidx.lifecycle.*
import com.teguh.githubuserfinal.database.User
import com.teguh.githubuserfinal.repository.UsersRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UsersRepository) : ViewModel() {

    fun getAllUsers() = repository.getAllUsers()

    fun insert(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun delete(user: User){
        viewModelScope.launch {
            repository.delete(user)
        }
    }
}


