package com.example.sub1githubuser

import com.google.gson.annotations.SerializedName


data class FollowResponseItem(

    @field:SerializedName("login")
    val login: String,


    @field:SerializedName("avatar_url")
    val avatarUrl: String,


    )
