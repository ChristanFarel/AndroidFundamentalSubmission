package com.example.sub1githubuser.ui.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.sub1githubuser.R
import com.example.sub1githubuser.database.local.entity.FavoriteEntity
import com.example.sub1githubuser.database.remote.response.DetailResponse
import com.example.sub1githubuser.database.remote.retrofit.ApiConfig
import com.example.sub1githubuser.databinding.ActivityDetailUserBinding
import com.example.sub1githubuser.repository.FavRepository
import com.example.sub1githubuser.ui.adapter.ListGHuserAdapter
import com.example.sub1githubuser.ui.adapter.SectionsPagerAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserActivity : AppCompatActivity() {

    private var favorite: FavoriteEntity? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    private lateinit var shimLayout: ShimmerFrameLayout

    private lateinit var binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mNoteRepository: FavRepository = FavRepository(application)

        supportActionBar?.title = getString(R.string.github_user_detail)


        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimLayout = binding.shimLayout

        shimLayout.startShimmer()


        val username = intent.getStringExtra(ListGHuserAdapter.USERNAME)


        if (username != null) {
            detail(username)
//            favorite= FavoriteEntity(0,"Farel","haha",true)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.fabFav.setOnClickListener {
            favorite?.let { it1 -> mNoteRepository.insert(it1) }
            Toast.makeText(this,"Berhasil ditambahkan",Toast.LENGTH_LONG).show()
        }


    }

    private fun detail(cariUser: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUsername(cariUser)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.detailUsername.text = responseBody.login

                        if(responseBody.company != null ){
                            binding.detailCompany.text = responseBody.company
                        } else{
                            binding.detailCompany.text = "Unidentified"
                        }

                        binding.detailFollower.text = responseBody.followers.toString()
                        binding.detailFollowing.text = responseBody.following.toString()
                        if (responseBody.location == null){
                            binding.detailLocation.text = "Unidentified"
                        } else{
                            binding.detailLocation.text = responseBody.location.toString()
                        }
                        binding.detailRepository.text = responseBody.publicRepos.toString()

                        if(responseBody.name != null){
                            binding.detaiNama.text = responseBody.name.toString()
                        }else{
                            binding.detaiNama.text = "Unidentified name"
                        }


                        Glide.with(this@DetailUserActivity)
                            .load(responseBody.avatarUrl)
                            .into(binding.detailGambar)

                        favorite = FavoriteEntity(0,responseBody.login, responseBody.avatarUrl)

                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar1.visibility = View.VISIBLE
        } else {
            binding.progressBar1.visibility = View.GONE
            shimLayout.stopShimmer()
            shimLayout.visibility = View.GONE

        }
    }
}