package com.example.sub1githubuser

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sub1githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var rcyUser: RecyclerView
    private val list = ArrayList<GHuser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        rcyUser = findViewById(R.id.rcyUser)
        rcyUser.setHasFixedSize(true)


        list.addAll(listUser)
        showRecyclerList()
    }

    private fun findUser() {
//        showLoading(true)
        val client = ApiConfig.getApiService().getUser("ChristanFarel")
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
//                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
//                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
//    private fun setRestaurantData(restaurant: User) {
//        binding.tvTitle.text = restaurant.name
//        binding.tvDescription.text = restaurant.description
//        Glide.with(this@MainActivity)
//            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
//            .into(binding.ivPicture)
//    }
    private fun setReviewData(allUser: List<ItemsItem>) {
        val listUser = ArrayList<GHuser>()
        for (user1 in allUser) {
            val us = GHuser("coba", user1.login, user1.avatarUrl, user1.score.toString(), user1.score.toString(), )
//            listUser.add("coba",user.login)
//            listUser.add(
//                """
//                ${review.review}
//                - ${review.name}
//                """.trimIndent()
//            )
        }
        val adapter = ReviewAdapter(listReview)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private val listUser: ArrayList<GHuser>
        get() {
            val dataNama = resources.getStringArray(R.array.data_nama)
            val datausername = resources.getStringArray(R.array.data_username)
            val dataFollower = resources.getStringArray(R.array.data_follower)
            val dataFollowing = resources.getStringArray(R.array.data_following)
            val dataFoto = resources.obtainTypedArray(R.array.data_foto)
            val dataLoc = resources.getStringArray(R.array.data_location)
            val dataRepo = resources.getStringArray(R.array.data_repo)
            val dataCompany = resources.getStringArray(R.array.data_company)

            val listUser = ArrayList<GHuser>()
            for (i in dataNama.indices) {
                val user = GHuser(dataNama[i],datausername[i],dataFoto.getResourceId(i,0),dataFollower[i],dataFollowing[i],dataCompany[i], dataLoc[i], dataRepo[i])
                listUser.add(user)
            }
            return listUser
        }

    private  fun showRecyclerList(){
        rcyUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListGHuserAdapter(list)
        rcyUser.adapter  = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListGHuserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GHuser) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: GHuser) {
        val semua = GHuser(user.nama,
            user.username,
            user.foto,
            user.follower,
            user.following,
            user.company,
            user.location,
            user.repo
            )
        val Intent = Intent(this, DetailUser::class.java)

        Intent.putExtra("All", semua)
        startActivity(Intent)
    }
}