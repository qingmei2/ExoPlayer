package com.github.qingmei2.exoplayer.multi_track.ui

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.github.qingmei2.exoplayer.multi_track.entity.SongPartItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.util.*

interface IPartItemController : LifecycleEventObserver {

    fun onBindItem(pos: Int, trackItem: SongPartItem, switchCallback: OnTrackSwitchChangedCallback)

    fun isTrackOpen(): Boolean

    fun setTrackOpen(isOpen: Boolean)

    fun onSeek(progress: Int, byUser: Boolean = false)

    fun onClickPlay()

    fun onClickPause()
}

typealias OnTrackSwitchChangedCallback = (Boolean) -> Unit

class SinglePartItemController(context: Context,
                               private val position: Int,
                               private val item: SongPartItem) : IPartItemController {


    private val mExoPlayer: SimpleExoPlayer

    private var mSwitchChangedCallback: OnTrackSwitchChangedCallback? = null

    private var isTrackOpen: Boolean = true

    private val mHandler = Handler(Looper.myLooper()!!)

    init {
        mExoPlayer = SimpleExoPlayer.Builder(context).build()
                .apply {
                    addMediaItem(MediaItem.fromUri(Uri.parse(item.partPath)))
                    prepare()
                }
    }

    override fun onBindItem(pos: Int,
                            trackItem: SongPartItem,
                            switchCallback: OnTrackSwitchChangedCallback) {
        this.mSwitchChangedCallback = switchCallback
    }

    override fun setTrackOpen(isOpen: Boolean) {
        isTrackOpen = isOpen
        if (!mExoPlayer.isPlaying) {
            mExoPlayer.play()
            mSwitchChangedCallback?.invoke(true)
        } else {
            mExoPlayer.pause()
            mSwitchChangedCallback?.invoke(false)
        }
    }

    override fun isTrackOpen(): Boolean {
        return isTrackOpen
    }

    override fun onSeek(@IntRange(from = 0, to = 100) progress: Int, byUser: Boolean) {
        mExoPlayer.volume = progress / 100f
    }

    override fun onClickPlay() {
        if (!mExoPlayer.isPlaying) {
            mExoPlayer.play()
        }
    }

    override fun onClickPause() {
        if (mExoPlayer.isPlaying) {
            mExoPlayer.pause()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                mExoPlayer.stop()
                mExoPlayer.release()
            }
        }
    }
}