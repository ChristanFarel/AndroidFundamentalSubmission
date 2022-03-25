package com.example.sub1githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sub1githubuser.FollowResponseItem
import com.example.sub1githubuser.databinding.ItemUserBinding
import com.example.sub1githubuser.ui.activity.DetailUserActivity

class FolAdapter(private val listUser: ArrayList<FollowResponseItem>) :
    RecyclerView.Adapter<FolAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val semua = listUser[position]
        val foto = semua.avatarUrl
        val username = semua.login

        Glide.with(holder.itemView.context)
            .load(foto)
            .into(holder.binding.itemGambar)

        holder.binding.itemUsername.text = username


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(ListGHuserAdapter.USERNAME, username)
            SectionsPagerAdapter.username = username
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = listUser.size


}