package com.example.sub1githubuser.repository

import androidx.lifecycle.LiveData
import com.example.sub1githubuser.database.local.entity.FavoriteEntity
import com.example.sub1githubuser.database.local.room.FavoriteDao
import com.example.sub1githubuser.database.remote.retrofit.ApiService
import com.example.sub1githubuser.util.AppExecutors
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//application: Application
class FavRepository(private val apiService: ApiService,
                    private val favDao: FavoriteDao,
                    private val appExecutors: AppExecutors){
//    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

//    init {
//        val db = FavDatabase.getDatabase(application)
//        mFavDao = db.favDao()
//    }

//    fun getAllFav(): LiveData<List<FavoriteEntity>> = mFavDao.getChoosedFav()

    fun insert(fav: FavoriteEntity){
        appExecutors.diskIO.execute{
            favDao.insertFav(fav)
        }
    }

    fun delete(fav: FavoriteEntity){
        appExecutors.diskIO.execute{
            favDao.delete(fav)
        }
    }

    fun getAll(): LiveData<List<FavoriteEntity>> = favDao.getFav()

    fun getUserName(userName: String): LiveData<Boolean> = favDao.checkUserName(userName)

    companion object {
        @Volatile
        private var instance: FavRepository? = null
        fun getInstance(
            apiService: ApiService,
            favDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavRepository =
            instance ?: synchronized(this) {
                instance ?: FavRepository(apiService, favDao, appExecutors)
            }.also { instance = it }
    }
}