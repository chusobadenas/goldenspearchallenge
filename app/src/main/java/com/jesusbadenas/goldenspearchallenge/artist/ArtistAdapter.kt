package com.jesusbadenas.goldenspearchallenge.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.databinding.ItemArtistBinding

class ArtistAdapter(private val viewPool: RecyclerView.RecycledViewPool) :
    PagingDataAdapter<Artist, ArtistAdapter.ArtistViewHolder>(ArtistDiffCallback()) {

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
        getItem(position)?.let { artist ->
            holder.bind(artist)
            loadAlbums(artist, holder.itemView)
        }
    }

    private fun loadAlbums(artist: Artist, view: View) {
        val albumAdapter = AlbumAdapter().apply {
            submitList(artist.albums)
        }
        view.findViewById<RecyclerView>(R.id.albums_rv).apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = albumAdapter
            setRecycledViewPool(viewPool)
        }
    }

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
                    oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.type == newItem.type
    }
}
