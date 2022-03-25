package com.example.sub1githubuser.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sub1githubuser.database.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(fav: FavoriteEntity)

    @Update
    fun updateFav(fav: FavoriteEntity)

    @Delete
    fun delete(fav: FavoriteEntity)

    @Query("SELECT * FROM favoriteUser ORDER BY userName")
    fun getFav(): LiveData<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT userName FROM favoriteUser WHERE userName = :userName)")
    fun checkUserName(userName: String): LiveData<Boolean>

}