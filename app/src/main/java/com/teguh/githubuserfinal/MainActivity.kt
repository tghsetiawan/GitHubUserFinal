package com.teguh.githubuserfinal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.teguh.githubuserfinal.databinding.ActivityMainBinding
import com.teguh.githubuserfinal.setting.SettingsDataStore
import com.teguh.githubuserfinal.setting.dataStore

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var layoutDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        layoutDataStore = SettingsDataStore(this.dataStore)

        layoutDataStore.getThemeSetting.asLiveData().observe(this) { }
        layoutDataStore.getThemeSetting.asLiveData().observe(this) { isLightModeActive: Boolean ->
            if (isLightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        setContentView(binding?.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navControl = this.findNavController(R.id.nav_host_fragment_container)
        return navControl.navigateUp()
    }
}