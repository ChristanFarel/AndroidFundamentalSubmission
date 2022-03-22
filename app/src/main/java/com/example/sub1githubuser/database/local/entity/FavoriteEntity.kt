package com.example.sub1githubuser.database.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteUser")
@Parcelize
data class FavoriteEntity (
    @PrimaryKey (autoGenerate = true)
    @field:ColumnInfo(name = "idUser")
    var id: Int = 0,

    @field:ColumnInfo(name = "userName")
    var username: String,

    @field:ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,

//    @field:ColumnInfo(name = "choosed")
//    var isChoosed: Boolean

) : Parcelable