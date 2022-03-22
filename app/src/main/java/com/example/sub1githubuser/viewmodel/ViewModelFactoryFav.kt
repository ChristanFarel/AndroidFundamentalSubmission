package com.example.sub1githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactoryFav private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactoryFav? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactoryFav {
            if (INSTANCE == null) {
                synchronized(ViewModelFactoryFav::class.java) {
                    INSTANCE = ViewModelFactoryFav(application)
                }
            }
            return INSTANCE as ViewModelFactoryFav
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}