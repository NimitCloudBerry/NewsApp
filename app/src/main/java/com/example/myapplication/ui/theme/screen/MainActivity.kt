package com.example.myapplication.ui.theme.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter

import com.example.demo.Util.ApiState
import com.example.myapplication.model.NewsResponse
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsList()
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalCoroutinesApi
@Composable
fun NewsList(newsViewModel: NewsViewModel = viewModel()) {
    LaunchedEffect(key1 = true) {
        newsViewModel.fetchNewsArticles()
    }


    val postState by remember { newsViewModel._postStateFlow }.collectAsState()


    when (val state = postState) {
        is ApiState.Loading -> {
            ShimmerEffect(modifier = Modifier.fillMaxSize())
            // Handle loading state
        }

        is ApiState.Failure -> {
            // Handle failure state
            Log.d("error", "onCreate: ${state.msg}")
        }

        is ApiState.Success -> {
            RecyclerView(modifier = Modifier.fillMaxSize(), articles = state.data.articles)
        }

        is ApiState.Empty -> {
            // Handle empty state if needed
        }
    }


}


@Composable
fun RecyclerView(modifier: Modifier, articles: List<NewsResponse.NewsArticle>?) {
    LazyColumn(modifier = modifier) {
        articles?.let {
            items(it.size) { index ->
                val article = it.getOrNull(index)
                article?.let { NewsItem(it) }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsItem(article: NewsResponse.NewsArticle) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                // Open URL in browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(article.urlToImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = article.title,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier.padding(bottom = 8.dp)
            )


            article.content?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            Text(
                text = "Published: ${article.publishedAt} by ${article.author}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}


@Composable
fun ShimmerEffect(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            repeat(10) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.LightGray,
                                    Color.Gray,
                                    Color.LightGray
                                ),
                                startX = 0f,
                                endX = 100f,
                            )
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


