package com.github.qingmei2.exoplayer.multi_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.qingmei2.exoplayer.multi_track.ui.MultiMusicActivity
import com.github.qingmei2.exoplayer.multi_track.ui.MultiTrackMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 同时播放多首音乐
        findViewById<View>(R.id.btn_multi_music).setOnClickListener(this::onMultiPlayerClicked)
        // 同时播放单曲多个音轨文件
        findViewById<View>(R.id.btn_single_song_list).setOnClickListener(this::onMultiPlayerClicked)
    }

    private fun onMultiPlayerClicked(view: View) {
        MultiMusicActivity.launch(this)
    }

    private fun onSingleSongListClicked(view: View) {
        MultiTrackMainActivity.launch(this)
    }
}