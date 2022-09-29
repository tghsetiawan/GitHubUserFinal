package com.teguh.githubuserfinal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.adapter.FollowingAdapter
import com.teguh.githubuserfinal.databinding.FragmentFollowingBinding
import com.teguh.githubuserfinal.viewmodel.UserDetailViewModel
import com.teguh.githubuserfinal.viewmodel.UserDetailViewModelFactory

class FollowingFragment : Fragment() {
    private var binding: FragmentFollowingBinding? = null
    private var viewAdapter: FollowingAdapter? = null
    private var viewModel: UserDetailViewModel? = null
    private var viewManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowingBinding.inflate(inflater)
        val args = SearchUserFragment.username
        val vmFactory = UserDetailViewModelFactory(args!!)

        viewModel = ViewModelProvider(this, vmFactory)[UserDetailViewModel::class.java]
        binding?.item = viewModel
        viewModel?.datafollowing?.observe(viewLifecycleOwner, Observer { list -> viewAdapter?.submitList(list) })

        viewModel?.isLoading?.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        //add reyclerview
        viewManager = LinearLayoutManager(activity)
        viewAdapter = FollowingAdapter()
        binding?.myRecyclerView?.adapter = viewAdapter
        binding?.myRecyclerView?.apply {
            this.layoutManager = viewManager
            adapter = viewAdapter
        }
        return binding?.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}