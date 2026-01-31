package com.bruno.appservicio.features.MealDB.presentation.screens

import com.bruno.appservicio.features.MealDB.domain.entities.Meal

data class MealUiState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String? = null,
)
