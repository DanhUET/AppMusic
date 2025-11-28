package com.danh.core_ui.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danh.core_ui.R

class SongBlurAdapter : RecyclerView.Adapter<SongBlurAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_song_blur,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

    }

    override fun getItemCount() = 15

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}