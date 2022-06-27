package com.github.qingmei2.exoplayer.multi_track.utils

import android.net.Uri
import com.google.android.exoplayer2.MediaItem

object DemoDataSources {

    object Assets {

        private const val SUMMER_MP3_URL = "asset:///media/summer.mp3"
        private const val HALF_MOON_MP3_URL = "asset:///media/half_moon.mp3"

        private const val SUNNY_MP4_URL = "asset:///media/sunny.mp4"

        // 菊次郎的夏天.mp3
        val SUMMER_MP3: MediaItem
            get() = MediaItem.fromUri(Uri.parse(SUMMER_MP3_URL))

        // 月半小夜曲.mp3
        val HALF_MOON_MP3: MediaItem
            get() = MediaItem.fromUri(Uri.parse(HALF_MOON_MP3_URL))

        // 晴天.mp4
        val SUNNY_MP4: MediaItem
            get() = MediaItem.fromUri(Uri.parse(SUNNY_MP4_URL))
    }
}