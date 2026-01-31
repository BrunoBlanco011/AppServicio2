package com.bruno.appservicio.features.MealDB.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bruno.appservicio.features.MealDB.domain.entities.Meal
import com.bruno.appservicio.features.MealDB.presentation.viewmodels.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealScreen(
    viewModel: MealViewModel,
    onMealClick: (Meal) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Paleta de colores Dark personalizada
    val backgroundColor = Color(0xFF121212)
    val cardColor = Color(0xFF1E1E1E)
    val primaryText = Color.White
    val secondaryText = Color(0xFFB0B0B0)
    val accentColor = Color(0xFFBB86FC)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Recetas del Mundo",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = primaryText
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1F1B24),
                    titleContentColor = primaryText
                )
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(color = accentColor)
                }
                uiState.error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¡Ups! Algo salió mal",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFCF6679) // Error color para modo oscuro
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.error!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = secondaryText
                        )
                    }
                }
                uiState.meals.isEmpty() -> {
                    Text(
                        text = "No se encontraron recetas",
                        style = MaterialTheme.typography.titleMedium,
                        color = secondaryText
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(uiState.meals) { meal ->
                            MealItem(
                                meal = meal,
                                backgroundColor = cardColor,
                                primaryTextColor = primaryText,
                                secondaryTextColor = accentColor,
                                onClick = { onMealClick(meal) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealItem(
    meal: Meal,
    backgroundColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = primaryTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Toca para ver la receta completa",
                    style = MaterialTheme.typography.bodySmall,
                    color = secondaryTextColor
                )
            }
        }
    }
}
