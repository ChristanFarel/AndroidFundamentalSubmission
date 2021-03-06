package com.example.sub1githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sub1githubuser.database.local.entity.FavoriteEntity
import com.example.sub1githubuser.repository.FavRepository

class FavViewModel(private val mFavRepository: FavRepository) : ViewModel() {

    fun getAll(): LiveData<List<FavoriteEntity>> = mFavRepository.getAll()

    fun delete(fav: FavoriteEntity) {
        mFavRepository.delete(fav)
    }
}