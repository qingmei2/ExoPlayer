package com.github.qingmei2.exoplayer.multi_track.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.qingmei2.exoplayer.multi_track.R
import com.github.qingmei2.exoplayer.multi_track.entity.SongPartItem

class MultiTrackListAdapter(
        diffUtil: DiffUtil.ItemCallback<SongPartItem> = MultiTrackListDiffUtil
) : ListAdapter<SongPartItem, MultiTrackListViewHolder>(diffUtil) {

    @MainThread
    fun bindItems(items: List<SongPartItem>) {
        this.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiTrackListViewHolder {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_listitem_song_track, parent, false)
                .let { MultiTrackListViewHolder(it) }
    }

    override fun onBindViewHolder(holder: MultiTrackListViewHolder, position: Int) {
        holder.binds(getItem(position))
    }
}

private object MultiTrackListDiffUtil : DiffUtil.ItemCallback<SongPartItem>() {

    override fun areItemsTheSame(oldItem: SongPartItem, newItem: SongPartItem): Boolean {
        return oldItem.partId == newItem.partId
    }

    override fun areContentsTheSame(oldItem: SongPartItem, newItem: SongPartItem): Boolean {
        return oldItem == newItem
    }
}

class MultiTrackListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var seekbar: SeekBar
    private lateinit var trackName: TextView

    init {
        seekbar = view.findViewById(R.id.seek_bar)
        trackName = view.findViewById(R.id.tv_track_name)
    }

    fun binds(trackItem: SongPartItem) {

    }
}