package com.github.qingmei2.exoplayer.multi_track.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.qingmei2.exoplayer.multi_track.R
import java.util.*

/**
 * 同时播放单曲多个音轨文件
 */
class MultiTrackMainActivity : AppCompatActivity() {

    companion object Launcher {

        fun launch(context: Context) {
            val intent = Intent(context, MultiTrackMainActivity::class.java)
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