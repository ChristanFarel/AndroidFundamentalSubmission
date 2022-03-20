package com.example.sub1githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sub1githubuser.databinding.ItemUserBinding

class FolAdapter (private val listUser: ArrayList<FollowResponseItem>) : RecyclerView.Adapter<FolAdapter.ListViewHolder>(){

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)


    private lateinit var onItemClickCallback: FolAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: FolAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolAdapter.ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FolAdapter.ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolAdapter.ListViewHolder, position: Int) {
        val semua = listUser[position]
        val foto = semua.avatarUrl
        val username = semua.login

        Glide.with(holder.itemView.context)
            .load(foto)
            .into(holder.binding.itemGambar)

        holder.binding.itemUsername.text = username


        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context ,DetailUser::class.java)
            intent.putExtra(ListGHuserAdapter.USERNAME, username)
            SectionsPagerAdapter.username = username
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GHuser)
    }


}