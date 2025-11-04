package com.practicum.listofpictures

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            var gallery by remember { mutableStateOf(generateSamplePictures()) }
            var isGridLayout by remember { mutableStateOf(false) }
            var searchText by remember { mutableStateOf("") } // №1

            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 30.dp)
                        ) {
                            TextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                label = { Text("Поиск по автору") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                placeholder = { Text("Автор") }
                            )
                        }

                        val searchByAuthorResults = if (searchText.isBlank()) {
                            gallery // Если поиск пустой - показываем всю галерею
                        } else {
                            // Фильтруем по автору (без учета регистра)
                            gallery.filter {
                                it.author.contains(searchText, ignoreCase = true)
                            }
                        }


                        if (isGridLayout) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                items(searchByAuthorResults.size) { index ->
                                    ShowItem(
                                        picture = searchByAuthorResults[index],
                                        onPressed = { pressedItem ->
                                            gallery = gallery.filter { it.id != pressedItem.id }
                                        }
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                items(searchByAuthorResults.size) { index ->
                                    ShowItem(
                                        picture = searchByAuthorResults[index],
                                        onPressed = { pressedItem ->
                                            gallery = gallery.filter { it.id != pressedItem.id }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(Modifier.weight(1f))
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                // .alpha(0.6f)
                                .clickable {
                                    //gallery = gallery.reversed()
                                    //isGridLayout =!isGridLayout
                                    val newImage = getNewImage() // Создание нового изображения
                                    // Проверка на существование изображения с таким же ID
                                    val imageExists = gallery.any { it.id == newImage.id }
                                    if (!imageExists) {
                                        gallery += newImage // Если такого ID еще нет, то обавляем в галерею
                                    }
                                },
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Toggle layout"
                        )

                        Spacer(Modifier.height(20.dp))

                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                // .alpha(0.6f)
                                .clickable {
                                    //gallery = gallery.reversed()
                                    isGridLayout = !isGridLayout
                                    //gallery += getNewImage()
                                },
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Toggle layout"
                        )

                        Spacer(Modifier.height(20.dp))

                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                // .alpha(0.6f)
                                .clickable {
                                    gallery = emptyList()
                                },
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Toggle layout"
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun ShowItem(
        //modifier: Modifier,
        picture: Picture,
        onPressed: (Picture) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { onPressed(picture) }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = picture.url,
                contentDescription = picture.author,
            ) {
                it
                    .placeholder(R.drawable.preview_photo) // Заглушка из drawable при загрузке
                    .error(R.drawable.preview_photo) // Заглушка из drawable при ошибке
            }

            Text(
                text = "Автор ${picture.author}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.Black,
                )
            )
        }

    }
}