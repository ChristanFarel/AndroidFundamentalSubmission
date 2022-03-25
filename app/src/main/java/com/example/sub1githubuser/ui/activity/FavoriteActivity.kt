package com.example.sub1githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub1githubuser.R
import com.example.sub1githubuser.databinding.ActivityFavoriteBinding
import com.example.sub1githubuser.di.Injection
import com.example.sub1githubuser.ui.adapter.FavoriteAdapter
import com.example.sub1githubuser.ui.viewmodel.FavViewModel
import com.example.sub1githubuser.ui.viewmodel.ViewModelFactoryFav

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.ghUser_fav_title)

        val favRepository = Injection.provideRepository(this)
        val favViewModel = obtainViewModel(this@FavoriteActivity)

        favViewModel.getAll().observe(this, {
            showLoading(true)
            if (it != null) {
                showLoading(false)
                if (it.isEmpty()) {
                    binding?.txtEmptyFav?.text = getString(R.string.empty_msg_fav)
                } else {
                    binding?.txtEmptyFav?.text = ""
                }
                adapter.submitList(it)
            }
        })

        adapter = FavoriteAdapter {
            favRepository.delete(it)
        }

        binding?.rcyFav?.setHasFixedSize(true)
        binding?.rcyFav?.layoutManager = LinearLayoutManager(this)
        binding?.rcyFav?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.pBarFav?.visibility = View.VISIBLE
        } else {
            binding?.pBarFav?.visibility = View.GONE

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = ViewModelFactoryFav.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeInFav -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.settingInFav -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

}