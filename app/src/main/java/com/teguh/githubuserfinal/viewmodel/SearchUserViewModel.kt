package com.teguh.githubuserfinal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teguh.githubuserfinal.network.GithubApi
import com.teguh.githubuserfinal.network.SearchUserBase
import com.teguh.githubuserfinal.network.SearchUserItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchUserViewModel : ViewModel() {
    private val _data = MutableLiveData<SearchUserBase>()
    private val _items = MutableLiveData<List<SearchUserItems>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val data: LiveData<SearchUserBase>
        get() = _data

    val items: LiveData<List<SearchUserItems>>
        get() = _items

    val isLoading: LiveData<Boolean> = _isLoading

    private var vmJob = Job()
    private val crScope = CoroutineScope(vmJob + Dispatchers.Main)

    fun searchGithubUser(username: String){
        crScope.launch {
            try {
                _isLoading.value = true
                Log.d("searchGithubUser: ", username)
                val resultData = GithubApi.retrofitService?.searchGithubUser(username)
                val resultItems = GithubApi.retrofitService?.searchGithubUser(username)?.items

                if(!resultData?.incomplete_results!!){
                    _isLoading.value = false
                    _items.value = resultItems!!
                    Log.d("searchGithubUser: ", resultData.toString())
                }
            } catch (t: Throwable) {
                _isLoading.value = false
                Log.d("searchGithubUser: ", "gagal $t")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}