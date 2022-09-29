package com.teguh.githubuserfinal.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.R
import com.teguh.githubuserfinal.adapter.FavoriteAdapter
import com.teguh.githubuserfinal.databinding.FragmentFavoriteUserBinding
import com.teguh.githubuserfinal.viewmodel.FavoriteViewModel
import com.teguh.githubuserfinal.viewmodel.FavoriteViewModelFactory

class FavoriteUserFragment : Fragment() {
    private var binding: FragmentFavoriteUserBinding? = null
    private var viewAdapter: FavoriteAdapter? = null
    private var viewModel: FavoriteViewModel? = null
    private var viewManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title= resources.getString(R.string.favorite_user)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val vmFactory = FavoriteViewModelFactory.getInstance(requireActivity())

        viewModel = ViewModelProvider(this, vmFactory)[FavoriteViewModel::class.java]
        binding?.item = viewModel
        viewModel?.getAllUsers()?.observe(viewLifecycleOwner, Observer { list ->
            viewAdapter?.submitList(list)
        })

        //add reyclerview
        viewManager = LinearLayoutManager(activity)
        viewAdapter = FavoriteAdapter(viewModel!!) { item -> showDetail(item)}
        binding?.myRecyclerView?.adapter = viewAdapter
        binding?.myRecyclerView?.apply {
            this.layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
        val item = menu.findItem(R.id.menuFavorite)
        item.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showDetail(name: String){
        this.findNavController().navigate(FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailUserFragment(name))
        SearchUserFragment.username = name
    }

}