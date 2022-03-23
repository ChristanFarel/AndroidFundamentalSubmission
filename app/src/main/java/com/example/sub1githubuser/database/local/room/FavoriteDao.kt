package com.example.sub1githubuser.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sub1githubuser.database.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {



//    @Query("SELECT * FROM favoriteUser where choosed = 1")
//    fun getChoosedFav(): LiveData<List<FavoriteEntity>>

//    @Query("SELECT * from favoriteUser ORDER BY idUser ASC")
//    fun getAll(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(fav: FavoriteEntity)

    @Update
    fun updateFav(fav: FavoriteEntity)

//    @Query("DELETE FROM favoriteUser WHERE choosed = 0")
//    fun deleteAll()

    @Delete
    fun delete(fav: FavoriteEntity)

    @Query("SELECT * FROM favoriteUser ORDER BY userName")
    fun getFav(): LiveData<List<FavoriteEntity>>

//    @Query("SELECT EXISTS(SELECT * FROM favoriteUser WHERE userName = :userName AND choosed = 1)")
//    fun isFavChoosed(userName: String): Boolean
}