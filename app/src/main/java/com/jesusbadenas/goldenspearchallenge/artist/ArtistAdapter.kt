package com.jesusbadenas.goldenspearchallenge.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.databinding.ItemArtistBinding

class ArtistAdapter : ListAdapter<Artist, ArtistAdapter.ArtistViewHolder>(ArtistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = DataBindingUtil.inflate<ItemArtistBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_artist,
            parent,
            false
        )
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = getItem(position)
        holder.bind(artist)
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) = with(itemView) {
            binding.artist = artist
            binding.executePendingBindings()
        }
    }

    internal class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.imageUrl == newItem.imageUrl
    }
}
