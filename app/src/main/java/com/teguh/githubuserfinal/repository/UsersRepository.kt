package com.teguh.githubuserfinal.repository

import androidx.lifecycle.LiveData
import com.teguh.githubuserfinal.database.*

class UsersRepository private constructor(private val userDao: UserDao){

    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    suspend fun insert(user: User){
        userDao.insert(user)
    }

    suspend fun delete(user: User){
        userDao.deleteSpecificUser(user.githubId.toInt())
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            usersDao: UserDao
        ): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository( usersDao)
            }.also { instance = it }
    }
}