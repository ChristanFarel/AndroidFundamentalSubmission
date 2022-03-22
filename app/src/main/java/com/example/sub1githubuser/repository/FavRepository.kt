package com.example.sub1githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sub1githubuser.database.local.entity.FavoriteEntity
import com.example.sub1githubuser.database.local.room.FavDatabase
import com.example.sub1githubuser.database.local.room.FavoriteDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application){
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

//    fun getAllFav(): LiveData<List<FavoriteEntity>> = mFavDao.getChoosedFav()

    fun insert(fav: FavoriteEntity){
        executorService.execute{mFavDao.insertFav(fav)}
    }

    fun delete(fav: FavoriteEntity){
        executorService.execute{mFavDao.delete(fav)}
    }

    fun getAll(): LiveData<List<FavoriteEntity>> = mFavDao.getFav()
}