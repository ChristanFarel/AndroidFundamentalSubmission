package com.example.sub1githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GHuser(
    var nama: String,
    var username: String,
    var foto: String,
    var follower: String,
    var following: String,
    var company: String,
    var location: String,
    var repo: String
) : Parcelable
