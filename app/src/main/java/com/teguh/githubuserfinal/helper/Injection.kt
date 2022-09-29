package com.teguh.githubuserfinal.helper

import android.content.Context
import com.teguh.githubuserfinal.database.UserRoomDatabase
import com.teguh.githubuserfinal.repository.UsersRepository

object Injection {
    fun provideRepository(context: Context): UsersRepository {
        val database = UserRoomDatabase.getDatabase(context)
        val dao = database.userDao()
        return UsersRepository.getInstance(dao)
    }
}