package com.teguh.githubuserfinal.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teguh.githubuserfinal.R
import com.teguh.githubuserfinal.adapter.SearchUserAdapter
import com.teguh.githubuserfinal.databinding.FragmentSearchUserBinding
import com.teguh.githubuserfinal.viewmodel.SearchUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch

class SearchUserFragment : Fragment() {
    companion object {
        var username: String? = null
    }

    private lateinit var viewAdapter: SearchUserAdapter
    private lateinit var viewModel : SearchUserViewModel

    private var viewManager: RecyclerView.LayoutManager? = null
    private var binding: FragmentSearchUserBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchUserBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title= resources.getString(R.string.github_search_user)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]
        binding?.lifecycleOwner = this
        binding?.viewModelSearchUser = viewModel

        viewAdapter = SearchUserAdapter { item -> showDetail(item) }

        viewModel.items.observe(viewLifecycleOwner, Observer { list -> viewAdapter.submitList(list) })

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        binding?.searchView?.setOnQueryTextListener( object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        viewModel.searchGithubUser(query)
                        viewManager = LinearLayoutManager(activity)
                        viewAdapter = SearchUserAdapter { item -> showDetail(item) }
                        binding?.myRecyclerView?.adapter = viewAdapter
                        binding?.myRecyclerView?.apply {
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }
                    } catch (e: TimeoutCancellationException) {
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
            R.id.menuSetting -> {
                findNavController().navigate(R.id.action_searchUserFragment_to_settingFragment, null, NavOptions.Builder().setPopUpTo(null, true).build())
                return true
            }
            R.id.menuFavorite -> {
                findNavController().navigate(R.id.action_searchUserFragment_to_favoriteUserFragment, null, NavOptions.Builder().setPopUpTo(null, true).build())
                return true
            }
            else -> return true
        }
    }

    fun showDetail(name: String){
        this.findNavController().navigate(SearchUserFragmentDirections.actionSearchUserFragmentToDetailUserFragment(name))
        username = name

    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}