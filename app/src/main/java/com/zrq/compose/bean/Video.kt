package com.zrq.compose.bean

data class Video(
    val code: Int,
    val count: Int,
    val data: List<VideoData>,
    val msg: String
)

data class VideoData(
    val actor: String,
    val cover: String,
    val descs: String,
    val director: String,
    val region: String,
    val releaseTime: String,
    val title: String,
    val updateTime: String,
    val videoId: String,
    val videoType: String,
    var isExpend: Boolean,
)