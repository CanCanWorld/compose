package com.zrq.compose

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zrq.compose.bean.*
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.*
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class MainModel @Inject constructor(
    private val application: Application,
    private val apiService: ApiService
) : ViewModel() {

    var text by mutableStateOf("")
    val videoItems by mutableStateOf(mutableStateListOf<VideoData>())
    var isShowChapter by mutableStateOf(false)
    var clickItem by mutableStateOf(
        VideoData(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    )

    val chapterItems by mutableStateOf(mutableStateListOf<Chapter>())
    var isLoadingFinish by mutableStateOf(false)

    fun search(keyword: String) {
        apiService.getVideo(keyword, 1, 30).enqueue(object : Callback<Video> {
            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                response.body()?.let { video ->
                    Log.d(TAG, "onResponse: ${video.data}")
                    videoItems.clear()
                    videoItems.addAll(video.data)
                }
            }

            override fun onFailure(call: Call<Video>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun loadChapter(vid: String) {
        isLoadingFinish = false
        apiService.getVideoChapter(vid).enqueue(object : Callback<VideoChapter> {
            override fun onResponse(call: Call<VideoChapter>, response: Response<VideoChapter>) {
                response.body()?.let { chapter ->
                    Log.d(TAG, "onResponse: $chapter")
                    chapterItems.clear()
                    chapterItems.addAll(chapter.data.chapterList)
                    isLoadingFinish = true
                }
            }

            override fun onFailure(call: Call<VideoChapter>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private companion object {
        const val TAG = "MainModel"
    }
}