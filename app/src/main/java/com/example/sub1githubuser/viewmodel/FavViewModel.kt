package com.example.sub1githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sub1githubuser.database.local.entity.FavoriteEntity
import com.example.sub1githubuser.repository.FavRepository

class FavViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)
    fun getAll(): LiveData<List<FavoriteEntity>> = mFavRepository.getAll()
}