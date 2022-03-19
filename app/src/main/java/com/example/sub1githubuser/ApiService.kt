package com.example.sub1githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users?=q{username}")
    fun getUser(
        @Path("login") login: String
    ): Call<SearchResponse>
}