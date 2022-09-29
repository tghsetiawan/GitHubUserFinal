package com.teguh.githubuserfinal.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY github_id ASC")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM user_table ORDER BY github_id ASC")
    fun getUsersAll(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("DELETE FROM user_table WHERE github_id =:id")
    suspend fun deleteSpecificUser(id:Int)
}