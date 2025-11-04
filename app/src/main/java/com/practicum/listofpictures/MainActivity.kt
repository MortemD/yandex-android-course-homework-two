package com.practicum.listofpictures

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
        enableEdgeToEdge()
        setContent {
            var gallery by remember { mutableStateOf(generateSamplePictures()) }
            var isGridLayout by remember {mutableStateOf(false)}
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isGridLayout) {
                        LazyVerticalGrid (
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(innerPadding)
                        ){
                            items(gallery.size) { index ->
                                ShowItem(
                                    picture = gallery[index],
                                    onPressed = { pressedItem ->
                                        gallery = gallery.filter {it.id != pressedItem.id}
                                    }
                                )
                            }
                        }
                    }
                    else {
                        LazyColumn(
                            modifier = Modifier.padding(innerPadding)
                        ){
                            items(gallery.size) { index ->
                                ShowItem(
                                    picture = gallery[index],
                                    onPressed = { pressedItem ->
                                        gallery = gallery.filter {it.id != pressedItem.id}
                                    }
                                )
                            }
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(Modifier.weight(1f))
                        Icon(
                            modifier = Modifier
                                .size(72.dp)
                                // .alpha(0.6f)
                                .clickable {
                                    gallery = gallery.reversed()
                                    isGridLayout =!isGridLayout
                                    gallery += getNewImage()
                                },
                            imageVector = Icons.Filled.AddCircle,
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