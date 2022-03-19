package com.example.sub1githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub1githubuser.databinding.ActivityDetailUserBinding


class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle("Github User Detail")

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val semua = intent.getParcelableExtra<GHuser>("All") as GHuser

        binding.detailNama.text = semua.nama
        binding.detailUsername.text = semua.username
        binding.detailCompany.text = semua.company
        binding.detailFollower.text = semua.follower
        binding.detailFollowing.text = semua.following
        binding.detailLocation.text = semua.location
        binding.detailRepository.text = semua.repo
        Glide.with(this).load(semua.foto).apply(RequestOptions().override(200, 200)).into(binding.detailGambar)


    }
}