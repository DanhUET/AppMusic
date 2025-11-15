package com.danh.feature_album.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danh.core_network.model.albums.Album
import com.danh.core_ui.databinding.ItemAlbumHotBinding
import com.danh.core_ui.databinding.ItemSongBinding

class AlbumHotAdapter(private val albumList: MutableList<Album>) :
    RecyclerView.Adapter<AlbumHotAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumHotAdapter.ViewHolder {
        val binding =
            ItemAlbumHotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumHotAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount() = albumList.size

    class ViewHolder(private val binding: ItemAlbumHotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.tvTitle.text = album.name
            Glide.with(binding.root).load(album.image).into(binding.imageAlbum)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumHot(albums: List<Album>) {
        albumList.clear()
        albumList.addAll(albums)
        notifyDataSetChanged()
    }

}