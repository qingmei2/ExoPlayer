package com.github.qingmei2.exoplayer.multi_track.entity

data class SongItem(val songName: String,
                    val singerName: String,
                    val partItems: List<SongPartItem>)


data class SongPartItem(val partName: String,
                        val partType: String,
                        val partPath: String,
                        val mainType: Boolean,
                        val defaultPlay: Boolean)