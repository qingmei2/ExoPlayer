package com.github.qingmei2.exoplayer.multi_track.utils

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import com.github.qingmei2.exoplayer.multi_track.entity.SongItem
import com.google.android.exoplayer2.MediaItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

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

        fun getSongs(context: Context): List<SongItem> {
            val stringBuilder = StringBuilder()
            //获得assets资源管理器
            val assetManager: AssetManager = context.assets
            //使用IO流读取json文件内容
            try {
                val bufferedReader = BufferedReader(InputStreamReader(
                        assetManager.open("media/songs.json"), "utf-8"))
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val json = stringBuilder.toString()

            val type = object : TypeToken<List<SongItem>>() {}.type
            return Gson().fromJson(json, type)
        }
    }
}