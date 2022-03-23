package com.example.sub1githubuser.database.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sub1githubuser.database.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavDatabase : RoomDatabase() {
    abstract fun favDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavDatabase {
            if (instance == null) {
                synchronized(FavDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavDatabase::class.java, "fav_database")
                        .build()
                }
            }
            return instance as FavDatabase
        }

    }
}
