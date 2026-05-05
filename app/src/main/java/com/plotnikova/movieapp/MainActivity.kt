package com.plotnikova.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plotnikova.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InteractiveMovieApp()
                }
            }
        }
    }
}

val movies = listOf(
    Movie(1, "Побег из Шоушенка", 1994, "Драма", 9.3, "Фрэнк Дарабонт",
        "Два заключённых находят дружбу и искупление за десятилетия ада."),
    Movie(2, "Крёстный отец", 1972, "Криминал, Драма", 9.2, "Фрэнсис Коппола",
        "Патриарх мафиозной семьи передаёт контроль над империей сыну."),
    Movie(3, "Тёмный рыцарь", 2008, "Экшн, Драма", 9.0, "Кристофер Нолан",
        "Бэтмен бросает вызов преступному миру Готэма."),
    Movie(4, "Криминальное чтиво", 1994, "Комедия, Драма", 8.9, "Квентин Тарантино",
        "Переплетающиеся истории гангстеров и боксёра.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveMovieApp() {
    var currentIndex by remember { mutableStateOf(0) }
    var showFullDescription by remember { mutableStateOf(false) }

    val currentMovie = movies[currentIndex]
    val totalCount = movies.size

    fun nextMovie() {
        currentIndex = (currentIndex + 1) % totalCount
        showFullDescription = false // сбрасываем описание при смене фильма
    }

    fun previousMovie() {
        currentIndex = if (currentIndex - 1 < 0) totalCount - 1 else currentIndex - 1
        showFullDescription = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои фильмы", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().height(280.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text("🎬", fontSize = 64.sp)
                    Text(currentMovie.title, color = Color.White, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(currentMovie.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("📅 ${currentMovie.year}")
                        Text("🎭 ${currentMovie.genre}")
                        Text("⭐ ${currentMovie.rating}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("🎬 Режиссёр: ${currentMovie.director}")
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("📖 Описание:", fontWeight = FontWeight.SemiBold)
                    Text(
                        if (showFullDescription) currentMovie.description
                        else currentMovie.description.take(80) + "...",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { previousMovie() }) { Text("◀ Назад") }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE0E0E0)
                ) {
                    Text(
                        "${currentIndex + 1} / $totalCount",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                }
                Button(onClick = { nextMovie() }) { Text("Вперёд ▶") }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📖 Показать подробное описание")
                Spacer(modifier = Modifier.width(12.dp))
                Switch(
                    checked = showFullDescription,
                    onCheckedChange = { showFullDescription = it }
                )
            }
        }
    }
}