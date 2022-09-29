package com.teguh.githubuserfinal.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.teguh.githubuserfinal.R
import com.teguh.githubuserfinal.adapter.MyPagerAdapter
import com.teguh.githubuserfinal.databinding.FragmentDetailUserBinding
import com.teguh.githubuserfinal.database.User
import com.teguh.githubuserfinal.viewmodel.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DetailUserFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_Follower,
            R.string.tab_text_Following
        )
    }

    private var binding: FragmentDetailUserBinding? = null
    private var myPagerAdapter: MyPagerAdapter? = null
    private var viewModel: UserDetailViewModel? = null
    private var currentDate: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailUserBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argUsername = DetailUserFragmentArgs.fromBundle(requireArguments()).username
        val vmFactory = UserDetailViewModelFactory(argUsername)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.detail_user)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, vmFactory)[UserDetailViewModel::class.java]

        binding?.lifecycleOwner = this
        binding?.viewModelDetailUser = viewModel

        myPagerAdapter = MyPagerAdapter(this)
        binding?.viewPager?.adapter = myPagerAdapter

        TabLayoutMediator(binding!!.tabs, binding!!.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel!!.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding?.fabFavorite?.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            currentDate = sdf.format(Date())

            try {
                val user = User(binding?.tvGithubId?.text.toString())
                user.githubId = binding?.tvGithubId?.text.toString()
                user.githubLogin = binding?.tvDetailUsername?.text.toString()
                user.githubName = binding?.tvDetailName?.text.toString()
                user.date = currentDate as String
                user.githubAvatarUrl = binding?.tvAvatarUrl?.text.toString()

                favoriteViewModel.insert(user)

                Toast.makeText(activity, "Berhasil menambahkan user ke daftar favorite", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }

//            if (binding?.tvDetailName?.text.isNullOrEmpty()) {
//                Toast.makeText(activity, "Note Title is Required", Toast.LENGTH_SHORT).show()
//            } else {
//
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
        val item = menu.findItem(R.id.menuFavorite)
        item.isVisible = false
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}