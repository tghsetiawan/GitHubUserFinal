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
import com.teguh.githubuserfinal.adapter.FollowerAdapter
import com.teguh.githubuserfinal.databinding.FragmentFollowerBinding
import com.teguh.githubuserfinal.viewmodel.UserDetailViewModel
import com.teguh.githubuserfinal.viewmodel.UserDetailViewModelFactory

class FollowerFragment : Fragment() {
    private var binding: FragmentFollowerBinding? = null
    private var viewAdapter: FollowerAdapter? = null
    private var viewModel: UserDetailViewModel? = null
    private var viewManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFollowerBinding.inflate(inflater)
        val args = SearchUserFragment.username
        val vmFactory = UserDetailViewModelFactory(args!!)

        viewModel = ViewModelProvider(this, vmFactory)[UserDetailViewModel::class.java]
        binding?.item = viewModel
        viewModel?.datafollower?.observe(viewLifecycleOwner, Observer { list -> viewAdapter?.submitList(list) })

        viewModel?.isLoading?.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        //add reyclerview
        viewManager = LinearLayoutManager(activity)
        viewAdapter = FollowerAdapter()
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