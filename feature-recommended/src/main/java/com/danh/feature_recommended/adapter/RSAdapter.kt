package com.danh.feature_recommended.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danh.core_network.model.song.Song
import com.danh.core_ui.databinding.ItemSongBinding
import com.danh.feature_recommended.R

class RSAdapter(val songList: MutableList<Song>) : RecyclerView.Adapter<RSAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    class ViewHolder(private val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.tvTitle.text = song.title
            binding.tvAirtist.text = song.artist
            Glide.with(binding.root)
                .load(song.image)
                .error(R.drawable.ic_error_music)
                .into(binding.image)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSongs(songs: List<Song>) {
        songList.clear()
        songList.addAll(songs)
        notifyDataSetChanged()
    }
}