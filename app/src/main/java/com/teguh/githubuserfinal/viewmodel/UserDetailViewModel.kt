package com.teguh.githubuserfinal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teguh.githubuserfinal.network.GithubApi
import com.teguh.githubuserfinal.network.UserFollower
import com.teguh.githubuserfinal.network.UserFollowing
import com.teguh.githubuserfinal.network.UserRespons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserDetailViewModel(username: String) : ViewModel() {
    private val _data = MutableLiveData<UserRespons>()
    private val _datafollower = MutableLiveData<List<UserFollower>>()
    private val _datafollowing = MutableLiveData<List<UserFollowing>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val data: LiveData<UserRespons>
        get() = _data

    val datafollower: LiveData<List<UserFollower>>
        get() = _datafollower

    val datafollowing : LiveData<List<UserFollowing>>
        get() = _datafollowing

    val isLoading: LiveData<Boolean> = _isLoading


    private var vmJob = Job()
    private val crScope = CoroutineScope(vmJob + Dispatchers.Main)

    init {
        searchDetailGithubUser(username)
    }

    private fun searchDetailGithubUser(username: String){
        crScope.launch {
            try {
                _isLoading.value = true
                Log.d("searchGithubUser: ", username)
                val resultData = GithubApi.retrofitService?.searchDetailGithubUser(username)

                _isLoading.value = false
                _data.value = resultData!!
                Log.d("searchGithubUser: ", resultData.toString())
                followerGithubUser(username)
                followingGithubUser(username)

            } catch (t: Throwable) {
                _isLoading.value = false
                Log.d("searchGithubUser: ", "gagal $t")
            }
        }
    }

    private fun followerGithubUser(username: String){
        crScope.launch {
            try {
                _isLoading.value = true
                Log.d("followerGithubUser: ", username)
                val resultData = GithubApi.retrofitService?.followerGithubUser(username)

                _isLoading.value = false
                _datafollower.value = resultData!!
                Log.d("followerGithubUser: ", resultData.toString())


            } catch (t: Throwable) {
                _isLoading.value = false
                Log.d("followerGithubUser: ", "gagal $t")
            }
        }
    }

    private fun followingGithubUser(username: String){
        crScope.launch {
            try {
                _isLoading.value = true
                Log.d("followerGithubUser: ", username)
                val resultData = GithubApi.retrofitService?.followingGithubUser(username)

                _isLoading.value = false
                _datafollowing.value = resultData!!
                Log.d("followerGithubUser: ", resultData.toString())

            } catch (t: Throwable) {
                _isLoading.value = false
                Log.d("followerGithubUser: ", "gagal $t")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}