package com.example.sub1githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sub1githubuser.databinding.ItemUserBinding

class ListGHuserAdapter( private val listUser: ArrayList<ItemsItem>) :  RecyclerView.Adapter<ListGHuserAdapter.ListViewHolder>(){

    companion object{
        const val USERNAME = "USERNAME"
    }

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, foto,) = listUser[position]

        Glide.with(holder.itemView.context)
            .load(foto)
            .into(holder.binding.itemGambar)

        holder.binding.itemUsername.text = username

        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context ,DetailUser::class.java)
            intent.putExtra(USERNAME, username)
            SectionsPagerAdapter.username = username
            holder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int = listUser.size



}