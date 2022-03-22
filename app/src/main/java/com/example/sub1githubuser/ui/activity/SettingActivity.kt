package com.example.sub1githubuser.ui.activity

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.sub1githubuser.R
import com.example.sub1githubuser.preferences.SettingPreferences
import com.example.sub1githubuser.viewmodel.SetPrefViewModel
import com.example.sub1githubuser.viewmodel.ViewModelFactorySetting

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val switchTheme = findViewById<Switch>(R.id.darkMode)

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
            switchTheme.isChecked = it
        })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            m.saveThemeSetting(isChecked)
        }


    }
}