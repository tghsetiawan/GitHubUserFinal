package com.teguh.githubuserfinal.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.teguh.githubuserfinal.R

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({ searchUserPage() }, 2000)
    }

    private fun searchUserPage() {
        lifecycleScope.launchWhenResumed {
            findNavController().navigate(R.id.action_splashScreenFragment_to_searchUserFragment, null, NavOptions.Builder().setPopUpTo(R.id.splashScreenFragment, true).build())
        }

    }
}