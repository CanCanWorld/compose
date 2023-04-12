package com.zrq.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zrq.compose.bean.Video
import com.zrq.compose.bean.VideoData
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.*
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class MainModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    var text by mutableStateOf("")
    val items by mutableStateOf(mutableStateListOf<VideoData>())

    fun search(keyword: String) {
        apiService.getVideo(keyword, 1, 30).enqueue(object : Callback<Video> {
            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                response.body()?.let { video ->
                    Log.d(TAG, "onResponse: ${video.data}")
                    items.clear()
                    items.addAll(video.data)
                }
            }

            override fun onFailure(call: Call<Video>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private companion object{
        const val TAG = "MainModel"
    }
}