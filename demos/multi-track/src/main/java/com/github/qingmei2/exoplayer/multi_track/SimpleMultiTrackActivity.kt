package com.github.qingmei2.exoplayer.multi_track

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.github.qingmei2.exoplayer.multi_track.common.SimpleSeekBarListener
import com.github.qingmei2.exoplayer.multi_track.utils.DemoDataSources
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.util.*

/**
 * 同时播放单曲多个音轨文件
 */
class SimpleMultiTrackActivity : AppCompatActivity() {

    companion object Launcher {

        fun launch(context: Context) {
            val intent = Intent(context, SimpleMultiTrackActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var mTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_track)

        initVideoPlayer()
    }

    private fun initVideoPlayer() {

    }
}