package com.zrq.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.zrq.compose.bean.Chapter
import com.zrq.compose.bean.VideoData
import com.zrq.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var vm: MainModel

    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            vm = viewModel()

            ComposeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Row {
                        TextField(value = vm.text, onValueChange = { vm.text = it })
                        Button(onClick = {
                            vm.search(vm.text)

                        }) {
                            Text(text = "搜索")
                        }
                    }

                    if (vm.isShowChapter) {
                        Item(video = vm.clickItem, vm)

                        if (vm.isLoadingFinish) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                items(vm.chapterItems) { chapter ->
                                    ChapterItem(chapter)
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                                Text(
                                    modifier = Modifier.padding(top = 20.dp),
                                    text = "加载中"
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                        ) {
                            items(vm.videoItems) {
                                Item(video = it, vm)
                            }
                        }
                    }
                }
            }
        }
    }


    private companion object {
        const val TAG = "MainActivity"
    }
}

@Composable
fun ChapterItem(chapter: Chapter) {
    Card(
        modifier = Modifier
            .padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 10.dp
            )
            .clickable { }
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = chapter.title,
        )
    }
}

@Composable
fun Item(video: VideoData, vm: MainModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 20.dp
            )
            .clickable {
                vm.isShowChapter = !vm.isShowChapter
                vm.clickItem = video
                if (vm.isShowChapter) {
                    vm.loadChapter(video.videoId)
                }
            }
    ) {
        Row {
            AsyncImage(
                model = video.cover,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ),
                    text = video.title
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ),
                    text = video.actor,
                    maxLines = 2
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ),
                    text = video.descs,
                    maxLines = 3,
                )
            }
        }
    }
}
