package com.github.qingmei2.exoplayer.multi_track.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.collection.ArrayMap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.qingmei2.exoplayer.multi_track.R
import com.github.qingmei2.exoplayer.multi_track.entity.SongPartItem
import com.google.android.material.switchmaterial.SwitchMaterial

class MultiTrackListAdapter(
        diffUtil: DiffUtil.ItemCallback<SongPartItem> = MultiTrackListDiffUtil
) : ListAdapter<SongPartItem, MultiTrackListViewHolder>(diffUtil), LifecycleEventObserver {

    private val mTrackControllers = ArrayMap<Int, IPartItemController>()

    @MainThread
    fun bindItems(items: List<SongPartItem>) {
        this.submitList(items)
    }

    @MainThread
    fun onClickPlay() {
        mTrackControllers.forEach { entry ->
            entry.value.onClickPlay()
        }
    }

    @MainThread
    fun onClickPause() {
        mTrackControllers.forEach { entry ->
            entry.value.onClickPause()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiTrackListViewHolder {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_listitem_song_track, parent, false)
                .let { MultiTrackListViewHolder(it) }
    }

    override fun onBindViewHolder(holder: MultiTrackListViewHolder, position: Int) {
        val item = getItem(position)
        val controller = getTrackController(holder.itemView.context, position, item)
        holder.binds(position, item, controller)
    }

    private fun getTrackController(context: Context, pos: Int, item: SongPartItem): IPartItemController {
        return when (mTrackControllers[pos] == null) {
            true -> {
                SinglePartItemController(context, pos, item).also {
                    mTrackControllers[pos] = it
                }
            }
            false -> mTrackControllers[pos]!!
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        mTrackControllers.forEach { entry ->
            entry.value.onStateChanged(source, event)
        }
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

    private val seekbar: SeekBar
    private val trackName: TextView
    private val trackSwitch: SwitchMaterial

    init {
        seekbar = view.findViewById(R.id.seek_bar)
        trackName = view.findViewById(R.id.tv_track_name)
        trackSwitch = view.findViewById(R.id.swt_track)
    }

    fun binds(pos: Int, trackItem: SongPartItem,
              controller: IPartItemController) {
        val isTrackOpen = controller.isTrackOpen()
        this.onTrackOpenChanged(isTrackOpen)

        trackName.text = trackItem.partName
        trackSwitch.isChecked = isTrackOpen
        trackSwitch.setOnCheckedChangeListener { _, isChecked ->
            controller.setTrackOpen(isChecked)
        }

        seekbar.progress = 100
        seekbar.max = 100
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, byUser: Boolean) {
                controller.onSeek(progress, byUser)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        val switchCallback: OnTrackSwitchChangedCallback = this::onTrackOpenChanged

        controller.onBindItem(pos, trackItem, switchCallback)
    }

    private fun onTrackOpenChanged(isTrackOpen: Boolean) {
        trackName.setTextColor(if (isTrackOpen) Color.BLACK else Color.GRAY)
    }
}