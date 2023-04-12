package com.zrq.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
                Column {

                    TextField(value = vm.text, onValueChange = { vm.text = it })
                    Button(onClick = {
                        vm.search(vm.text)

                    }) {
                        Text(text = "搜索")
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                    ) {
                        items(vm.items) {
                            Item(video = it)
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
fun Item(video: VideoData, isExpanded: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
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
                    text = video.actor
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ),
                    text = video.descs
                )
            }
        }
    }
}
