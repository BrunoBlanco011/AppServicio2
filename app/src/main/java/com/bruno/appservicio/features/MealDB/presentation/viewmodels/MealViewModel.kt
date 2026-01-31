package com.bruno.appservicio.features.MealDB.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno.appservicio.features.MealDB.domain.entities.Meal
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealDetailsUseCase
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealUseCase
import com.bruno.appservicio.features.MealDB.presentation.screens.MealUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MealUiState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String? = null
)

data class MealDetailUiState(
    val isLoading: Boolean = false,
    val meal: Meal? = null,
    val error: String? = null
)

class MealViewModel (
    private val getMealUseCase: GetMealUseCase,
    private val getMealDetailsUseCase: GetMealDetailsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MealUiState())
    val uiState = _uiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(MealDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    init {
        loadMeals()
    }

    private fun loadMeals() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        Log.d("MealViewModel", "loadMeals() called")
        viewModelScope.launch {
            try {
                val result = getMealUseCase()
                _uiState.update { currentState ->
                    result.fold(
                        onSuccess = { list ->
                            Log.d("MealViewModel", "Success: ${list.size} meals loaded")
                            currentState.copy(meals = list, isLoading = false)
                        },
                        onFailure = { error ->
                            Log.e("MealViewModel", "Error: ${error.message}")
                            currentState.copy(error = error.message, isLoading = false)
                        }

                    )
                }
            } catch (e: Exception) {
                Log.e("MealViewModel", "Error: ${e.message}")
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }

        }
    }

    fun selectMeal(meal: Meal) {
        _detailUiState.update { it.copy(isLoading = true, meal = meal, error = null) }

            viewModelScope.launch {
                try {
                    val result = getMealDetailsUseCase(meal.id)
                    _detailUiState.update { currentState ->
                        result.fold(
                            onSuccess = { fullmeal ->
                                currentState.copy(meal = fullmeal, isLoading = false)
                            },
                            onFailure = { error ->
                                currentState.copy(error = error.message, isLoading = false)
                            }
                        )

                    }

                } catch (e: Exception) {
                    _detailUiState.update { it.copy(error = e.message, isLoading = false) }
                }
            }
        }
    }






