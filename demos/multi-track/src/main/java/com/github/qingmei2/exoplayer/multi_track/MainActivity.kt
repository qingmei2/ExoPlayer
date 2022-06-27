package com.github.qingmei2.exoplayer.multi_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_multi_player).setOnClickListener(this::onMultiPlayerClicked)
    }

    private fun onMultiPlayerClicked(view: View) {
        MultiPlayerActivity.launch(this)
    }
}