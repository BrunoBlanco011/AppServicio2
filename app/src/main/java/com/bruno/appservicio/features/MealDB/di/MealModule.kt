package com.bruno.appservicio.features.MealDB.di

import com.bruno.appservicio.core.di.AppContainer
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealDetailsUseCase
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealUseCase
import com.bruno.appservicio.features.MealDB.presentation.viewmodels.MealViewModelFactory

class MealModule (
    private val appContainer: AppContainer) {

    private fun provideGetMealUseCase(): GetMealUseCase{
        return GetMealUseCase(appContainer.mealsRepository)
    }

    private fun provideGetMealDetailsUseCase(): GetMealDetailsUseCase {
        return GetMealDetailsUseCase(appContainer.mealsRepository)
    }


    fun provideMealsViewModelFactory(): MealViewModelFactory {
        return MealViewModelFactory(
            getMealUseCase = provideGetMealUseCase(),
            getMealDetailsUseCase = provideGetMealDetailsUseCase()
        )
    }
}


