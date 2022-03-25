package com.example.sub1githubuser.di

import android.content.Context
import com.example.sub1githubuser.database.local.room.FavDatabase
import com.example.sub1githubuser.database.remote.retrofit.ApiConfig
import com.example.sub1githubuser.repository.FavRepository
import com.example.sub1githubuser.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavDatabase.getDatabase(context)
        val favDao = database.favDao()
        val appExecutors = AppExecutors()
        return FavRepository.getInstance(apiService, favDao, appExecutors)
    }
}