package com.example.sub1githubuser.database.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteUser")
@Parcelize
data class FavoriteEntity(

    @PrimaryKey
    @field:ColumnInfo(name = "userName")
    var username: String,

    @field:ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,

    ) : Parcelable