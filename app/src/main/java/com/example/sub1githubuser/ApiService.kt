package com.example.sub1githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") login: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun  getUsername(
        @Path("username") name: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") name: String
    ): Call<ArrayList<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") name: String
    ): Call<ArrayList<FollowResponseItem>>
}