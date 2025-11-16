package com.danh.feature_album.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danh.core_network.model.song.Song
import com.danh.core_ui.databinding.ItemDetailAlbumBinding
import com.danh.feature_album.databinding.FragmentDetailAlbumBinding

class AlbumDetailAdapter(private val songList: MutableList<Song>) :
    RecyclerView.Adapter<AlbumDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumDetailAdapter.ViewHolder {
        val binding =
            ItemDetailAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AlbumDetailAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(songList[position])
    }

    override fun getItemCount() = songList.size
    class ViewHolder(private val binding: ItemDetailAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.tvItemTitle.text = song.title
            binding.tvItemArtist.text = song.artist
            Glide.with(binding.root).load(song.image).into(binding.imageItem)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSongs(songs: List<Song>) {
        songList.clear()
        songList.addAll(songs)
        notifyDataSetChanged()
    }
}