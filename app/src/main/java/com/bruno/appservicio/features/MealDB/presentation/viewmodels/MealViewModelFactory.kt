package com.bruno.appservicio.features.MealDB.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealDetailsUseCase
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealUseCase

class MealViewModelFactory (
    private val getMealUseCase: GetMealUseCase,
    private val getMealDetailsUseCase: GetMealDetailsUseCase
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealViewModel(getMealUseCase, getMealDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}