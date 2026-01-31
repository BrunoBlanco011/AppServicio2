package com.bruno.appservicio.features.MealDB.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bruno.appservicio.features.MealDB.presentation.viewmodels.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    viewModel: MealViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.detailUiState.collectAsState()

    // Paleta de colores Dark
    val backgroundColor = Color(0xFF121212)
    val cardBackground = Color(0xFF1E1E1E)
    val accentColor = Color(0xFFBB86FC)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFFB0B0B0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Detalles",
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1F1B24),
                    titleContentColor = textColor
                )
            )
        },
        containerColor = backgroundColor
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = accentColor
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = Color(0xFFCF6679),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                uiState.meal == null -> {
                    Text(
                        text = "No se ha seleccionado ninguna receta",
                        color = secondaryTextColor,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    val meal = uiState.meal!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Imagen con diseño expandido y bordes inferiores suaves
                        AsyncImage(
                            model = meal.imageUrl,
                            contentDescription = meal.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = meal.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = textColor,
                                lineHeight = 36.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Tarjeta de Instrucciones con estilo premium
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = cardBackground
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp, 24.dp)
                                                .background(accentColor, RoundedCornerShape(4.dp))
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Instrucciones",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = textColor
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.height(16.dp))
                                    
                                    Text(
                                        text = meal.instructions,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = secondaryTextColor,
                                        lineHeight = 28.sp,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}
