package com.danh.feature_artists.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danh.core_network.model.artist.Artist
import com.danh.core_network.model.song.Song
import com.danh.core_ui.databinding.ItemArtistBinding

class ArtistAdapter(private val artists:MutableList<Artist>,private val listener: OnClickItem): RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistAdapter.ViewHolder {
       val binding= ItemArtistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ArtistAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(artists[position]);
    }

    override fun getItemCount()=artists.size
    inner class ViewHolder(private val binding: ItemArtistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist){
            binding.tvName.text=artist.name
            Glide.with(binding.root).load(artist.avatar).into(binding.imgAvatar)
            binding.root.setOnClickListener {
                listener.clickItem(artists[bindingAdapterPosition].id)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateView(artistList:List<Artist>){
        artists.clear()
        artists.addAll(artistList)
        notifyDataSetChanged()
    }
    interface OnClickItem{
        fun clickItem(position: String)
    }
}