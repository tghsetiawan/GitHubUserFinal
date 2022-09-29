package com.teguh.githubuserfinal.fragment

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.switchmaterial.SwitchMaterial
import com.teguh.githubuserfinal.R
import com.teguh.githubuserfinal.databinding.FragmentSettingBinding
import com.teguh.githubuserfinal.setting.SettingsDataStore
import com.teguh.githubuserfinal.setting.dataStore
import kotlinx.coroutines.launch
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SettingFragment : Fragment() {
    private var binding: FragmentSettingBinding? = null
    private lateinit var layoutDataStore: SettingsDataStore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title= resources.getString(R.string.setting)

        val switchTheme: SwitchMaterial = binding!!.switchTheme

        layoutDataStore = SettingsDataStore(requireContext().dataStore)

        layoutDataStore.getThemeSetting.asLiveData().observe(viewLifecycleOwner) { }
        layoutDataStore.getThemeSetting.asLiveData().observe(viewLifecycleOwner) { isLightModeActive: Boolean ->
            if (isLightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            lifecycleScope.launch{
                layoutDataStore.saveThemeSetting(isChecked, requireContext())
            }
        }
    }
}