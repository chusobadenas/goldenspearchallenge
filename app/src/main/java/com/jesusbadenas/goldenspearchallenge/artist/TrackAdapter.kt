package com.jesusbadenas.goldenspearchallenge.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.data.model.Track
import com.jesusbadenas.goldenspearchallenge.databinding.ItemTrackBinding

class TrackAdapter : ListAdapter<Track, TrackAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = DataBindingUtil.inflate<ItemTrackBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_track,
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let { track ->
            holder.bind(track)
        }
    }

    override fun getItemCount(): Int = currentList.size

    class TrackViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) = with(itemView) {
            binding.track = track
            binding.executePendingBindings()
        }
    }

    internal class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.duration == newItem.duration &&
                    oldItem.type == newItem.type
    }
}
