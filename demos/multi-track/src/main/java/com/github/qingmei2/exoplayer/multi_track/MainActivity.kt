package com.github.qingmei2.exoplayer.multi_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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
        SimpleMultiTrackActivity.launch(this)
    }
}