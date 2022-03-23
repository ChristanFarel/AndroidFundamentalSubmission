package com.example.sub1githubuser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub1githubuser.databinding.ActivityFavoriteBinding
import com.example.sub1githubuser.di.Injection
import com.example.sub1githubuser.ui.adapter.FavoriteAdapter
import com.example.sub1githubuser.viewmodel.FavViewModel
import com.example.sub1githubuser.viewmodel.ViewModelFactoryFav

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val favRepository = Injection.provideRepository(this)
        val favViewModel = obtainViewModel(this@FavoriteActivity)

        favViewModel.getAll().observe(this,{
            if (it != null){
                adapter.submitList(it)
            }
        })

        adapter = FavoriteAdapter{
            favRepository.delete(it)
        }
        binding?.rcyFav?.setHasFixedSize(true)
        binding?.rcyFav?.layoutManager = LinearLayoutManager(this)
        binding?.rcyFav?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = ViewModelFactoryFav.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }

}