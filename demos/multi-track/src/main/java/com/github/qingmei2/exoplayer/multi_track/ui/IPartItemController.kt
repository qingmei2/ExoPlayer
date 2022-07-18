package com.github.qingmei2.exoplayer.multi_track.ui

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import com.github.qingmei2.exoplayer.multi_track.entity.SongPartItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.util.*

interface IPartItemController {

    fun onBindItem(pos: Int, trackItem: SongPartItem,
                   durationCallback: OnTrackDurationChangedCallback,
                   switchCallback: OnTrackSwitchChangedCallback)

    fun isTrackOpen(): Boolean

    fun setTrackOpen(isOpen: Boolean)

    fun onSeek(progress: Int, byUser: Boolean = false)

    fun onClickPlay()

    fun onClickPause()
}

typealias OnTrackDurationChangedCallback = (Float) -> Unit
typealias OnTrackSwitchChangedCallback = (Boolean) -> Unit

class SinglePartItemController(context: Context,
                               private val position: Int,
                               private val item: SongPartItem) : IPartItemController {


    private val mExoPlayer: ExoPlayer

    private var mTimer: Timer? = null
    private var mDurationCallback: OnTrackDurationChangedCallback? = null
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
                            durationCallback: OnTrackDurationChangedCallback,
                            switchCallback: OnTrackSwitchChangedCallback) {
        this.mDurationCallback = durationCallback
        this.mSwitchChangedCallback = switchCallback

        startTimer()
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
        if (byUser) {
            val targetMill = mExoPlayer.duration * (progress / 100.0)
            mExoPlayer.seekTo(targetMill.toLong())
        }
    }

    override fun onClickPlay() {
        if (!mExoPlayer.isPlaying) {
            mExoPlayer.play()
            startTimer()
        }
    }

    override fun onClickPause() {
        if (mExoPlayer.isPlaying) {
            mExoPlayer.pause()
            stopTimer()
        }
    }

    private fun startTimer() {
        stopTimer()
        mTimer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    mHandler.post {
                        if (mExoPlayer.isPlaying) {
                            val targetProgress = 100.0f * mExoPlayer.currentPosition / mExoPlayer.duration
                            mDurationCallback?.invoke(targetProgress)
                        }
                    }
                }
            }, 1000L, 1000L)
        }
    }

    private fun stopTimer() {
        mTimer?.apply {
            cancel()
            purge()
        }
        mTimer = null
    }
}