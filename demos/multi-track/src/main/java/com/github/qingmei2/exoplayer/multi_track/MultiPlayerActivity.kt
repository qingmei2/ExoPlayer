package com.github.qingmei2.exoplayer.multi_track

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.github.qingmei2.exoplayer.multi_track.utils.DemoDataSources
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.util.*

class MultiPlayerActivity : AppCompatActivity() {

    companion object Launcher {

        fun launch(context: Context) {
            val intent = Intent(context, MultiPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mBtnPlayTrack1: View
    private lateinit var mBtnPlayTrack2: View

    private lateinit var mSeekBar1: SeekBar
    private lateinit var mSeekBar2: SeekBar

    private lateinit var mExoPlayer1: ExoPlayer
    private lateinit var mExoPlayer2: ExoPlayer

    private lateinit var mExoVideoView: StyledPlayerView
    private lateinit var mExoVideoPlayer: ExoPlayer

    private var mTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_player)

        mBtnPlayTrack1 = findViewById(R.id.btn_play_track1)
        mBtnPlayTrack2 = findViewById(R.id.btn_play_track2)

        mSeekBar1 = findViewById(R.id.seek_bar_01)
        mSeekBar2 = findViewById(R.id.seek_bar_02)

        mExoVideoView = findViewById(R.id.player_view)

        initVideoPlayer()
        initAudioPlayer()
        initSeekbars()
    }

    private fun initVideoPlayer() {
        mExoVideoPlayer = SimpleExoPlayer.Builder(this).build()
                .apply {
                    mExoVideoView.player = this
                    addMediaItem(DemoDataSources.Assets.SUNNY_MP4)
                    prepare()
                }
    }

    private fun initAudioPlayer() {
        mExoPlayer1 = SimpleExoPlayer.Builder(this, DefaultRenderersFactory(this)).build()
                .apply {
                    addMediaItem(DemoDataSources.Assets.SUMMER_MP3)
                    prepare()
                }

        mExoPlayer2 = SimpleExoPlayer.Builder(this, DefaultRenderersFactory(this)).build()
                .apply {
                    addMediaItem(DemoDataSources.Assets.HALF_MOON_MP3)
                    prepare()
                }

        mBtnPlayTrack1.setOnClickListener {
            if (mExoPlayer1.isPlaying) {
                mExoPlayer1.pause()
            } else {
                mExoPlayer1.play()
            }
        }

        mBtnPlayTrack2.setOnClickListener {
            if (mExoPlayer2.isPlaying) {
                mExoPlayer2.pause()
            } else {
                mExoPlayer2.play()
            }
        }
    }

    private fun initSeekbars() {
        mSeekBar1.setOnSeekBarChangeListener(SimpleSeekBarListener(mExoPlayer1))
        mSeekBar2.setOnSeekBarChangeListener(SimpleSeekBarListener(mExoPlayer2))
    }

    private fun startTimer() {
        stopTimer()
        mTimer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    mSeekBar1.post {
                        val durationSec = mExoPlayer1.duration / 1000L
                        val curDurationSec = mExoPlayer1.currentPosition / 1000L
                        mSeekBar1.max = durationSec.toInt()
                        mSeekBar1.progress = curDurationSec.toInt()
                    }

                    mSeekBar2.post {
                        val durationSec1 = mExoPlayer2.duration / 1000L
                        val curDurationSec1 = mExoPlayer2.currentPosition / 1000L
                        mSeekBar2.max = durationSec1.toInt()
                        mSeekBar2.progress = curDurationSec1.toInt()
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

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mExoPlayer1.stop()
        mExoPlayer1.release()
        mExoPlayer2.stop()
        mExoPlayer2.release()
    }
}