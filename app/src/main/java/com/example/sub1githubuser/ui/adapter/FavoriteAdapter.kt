package com.example.sub1githubuser.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sub1githubuser.database.local.entity.FavoriteEntity
import com.example.sub1githubuser.databinding.ItemFavoriteBinding

// Dimasukkan menjadi parameter harusnya(private val onBookmarkClick: (FavoriteEntity) -> Unit)
class FavoriteAdapter : ListAdapter<FavoriteEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(favs: FavoriteEntity){
            binding.itemUsernameFavorite.text = favs.username
            Glide.with(itemView.context)
                .load(favs.urlToImage)
                .into(binding.itemGambarFavorite)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fav = getItem(position)
        holder.bind(fav)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEntity> =
            object : DiffUtil.ItemCallback<FavoriteEntity>() {
                override fun areItemsTheSame(oldFav: FavoriteEntity, newFav: FavoriteEntity): Boolean {
                    return oldFav.username == newFav.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldFav: FavoriteEntity, newFav: FavoriteEntity): Boolean {
                    return oldFav == newFav
                }
            }
    }


}