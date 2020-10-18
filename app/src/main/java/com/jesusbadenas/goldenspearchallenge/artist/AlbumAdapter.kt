package com.jesusbadenas.goldenspearchallenge.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.data.model.Album
import com.jesusbadenas.goldenspearchallenge.databinding.ItemAlbumBinding

class AlbumAdapter : ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = DataBindingUtil.inflate<ItemAlbumBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_album,
            parent,
            false
        )
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let { album ->
            holder.bind(album)
        }
    }

    override fun getItemCount(): Int = currentList.size

    class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        ViewHolder(binding.root) {
        fun bind(album: Album) = with(itemView) {
            binding.album = album
            binding.executePendingBindings()
        }
    }

    internal class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.type == newItem.type
    }
}
