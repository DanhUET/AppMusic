package com.danh.feature_recommended.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danh.core_network.model.song.Song
import com.danh.core_ui.databinding.ItemSongBinding

class RSAllAdapter(private val songList: MutableList<Song>, private val listener: OnClickItem) :
    RecyclerView.Adapter<RSAllAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RSAllAdapter.ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RSAllAdapter.ViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount() = songList.size
    inner class ViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.tvTitle.text = song.title
            binding.tvAirtist.text = song.artist
            Glide.with(binding.root).load(song.image).into(binding.image)
            binding.root.setOnClickListener {
                listener.playMusic(songList, bindingAdapterPosition)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllSongs(songs: List<Song>) {
        songList.clear()
        songList.addAll(songs)
        notifyDataSetChanged()
    }

    interface OnClickItem {
        fun playMusic(songList: List<Song>, position: Int)
    }

}