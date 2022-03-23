package com.example.sub1githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.sub1githubuser.R
import com.example.sub1githubuser.databinding.ActivitySettingBinding
import com.example.sub1githubuser.preferences.SettingPreferences
import com.example.sub1githubuser.viewmodel.SetPrefViewModel
import com.example.sub1githubuser.viewmodel.ViewModelFactorySetting



class SettingActivity : AppCompatActivity() {

    private var _activitySetBinding: ActivitySettingBinding? = null
    private val binding get() = _activitySetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySetBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val pref = SettingPreferences.getInstance(dataStore)
        val m = ViewModelProvider(this, ViewModelFactorySetting(pref))[SetPrefViewModel::class.java]

        m.getThemeSettings().observe(this
        ) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        m.getThemeSettings().observe(this,{
            binding?.darkMode?.isChecked = it
        })

        binding?.darkMode?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            m.saveThemeSetting(isChecked)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.homeInSetting -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.favInSetting -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}