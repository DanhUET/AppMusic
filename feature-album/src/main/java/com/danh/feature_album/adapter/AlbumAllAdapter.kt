package com.danh.feature_album.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danh.core_network.model.albums.Album
import com.danh.core_ui.databinding.ItemMoreAlbumBinding

class AlbumAllAdapter(
    private val albumsList: MutableList<Album>,
    private val listener: OnItemClick
) :
    RecyclerView.Adapter<AlbumAllAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumAllAdapter.ViewHolder {
        val binding =
            ItemMoreAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumAllAdapter.ViewHolder, position: Int) {
        holder.bind(albumsList[position])
    }

    override fun getItemCount() = albumsList.size

    inner class ViewHolder(private val binding: ItemMoreAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.title.text = album.name
            Glide.with(binding.root).load(album.image).into(binding.imageAlbum)
            binding.imageAlbum.setOnClickListener {
                listener.clickItem(album)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllAlbum(allAlbums: List<Album>) {
        albumsList.clear()
        albumsList.addAll(allAlbums)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun clickItem(album: Album)
    }
}