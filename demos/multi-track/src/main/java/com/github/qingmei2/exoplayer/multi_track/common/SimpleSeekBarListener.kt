package com.github.qingmei2.exoplayer.multi_track.common

import android.widget.SeekBar
import androidx.annotation.RestrictTo
import com.google.android.exoplayer2.Player

@RestrictTo(RestrictTo.Scope.LIBRARY)
class SimpleSeekBarListener(private val player: Player) : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (progress == 0 || seekBar.max == 0) return
        if (fromUser) {
            player.seekTo(progress * 1000L)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
    }
}