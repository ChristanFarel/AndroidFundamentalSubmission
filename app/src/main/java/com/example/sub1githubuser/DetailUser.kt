package com.example.sub1githubuser

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.sub1githubuser.databinding.ActivityDetailUserBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUser : AppCompatActivity() {

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

        supportActionBar?.setTitle(getString(R.string.github_user_detail))




        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimLayout = binding.shimLayout

        shimLayout.startShimmer()


        val username = intent.getStringExtra(ListGHuserAdapter.USERNAME)

        if (username != null) {
            detail(username)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

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
//                            showRecyclerList(responseBody.items)
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

                        Glide.with(this@DetailUser)
                            .load(responseBody.avatarUrl)
                            .into(binding.detailGambar)

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