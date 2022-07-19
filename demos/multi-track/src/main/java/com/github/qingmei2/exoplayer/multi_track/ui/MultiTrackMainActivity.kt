package com.github.qingmei2.exoplayer.multi_track.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.qingmei2.exoplayer.multi_track.MainActivity
import com.github.qingmei2.exoplayer.multi_track.R
import com.github.qingmei2.exoplayer.multi_track.utils.DemoDataSources
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

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListAdapter: MultiTrackListAdapter

    private lateinit var mBtnPlay: Button
    private lateinit var mBtnPause: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_track)

        mRecyclerView = findViewById(R.id.recyclerview)
        mBtnPlay = findViewById(R.id.btn_play)
        mBtnPause = findViewById(R.id.btn_pause)

        initList()

        mBtnPlay.setOnClickListener { mListAdapter.onClickPlay() }
        mBtnPause.setOnClickListener { mListAdapter.onClickPause() }
    }

    private fun initList() {
        val songItem = DemoDataSources.Assets.getSongs(this)[0]

        mListAdapter = MultiTrackListAdapter().apply {
            mRecyclerView.adapter = this
            mRecyclerView.layoutManager = LinearLayoutManager(this@MultiTrackMainActivity)

            lifecycle.addObserver(this)

            bindItems(songItem.partItems)
        }
    }
}