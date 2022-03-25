package com.example.sub1githubuser.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub1githubuser.di.Injection
import com.example.sub1githubuser.repository.FavRepository

class ViewModelFactoryFav private constructor(private val mFavRepository: FavRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactoryFav? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactoryFav {
            if (INSTANCE == null) {
                synchronized(ViewModelFactoryFav::class.java) {
                    INSTANCE = ViewModelFactoryFav(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactoryFav
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mFavRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}