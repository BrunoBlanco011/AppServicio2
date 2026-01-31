package com.bruno.appservicio.features.MealDB.domain.usecases

import com.bruno.appservicio.features.MealDB.domain.entities.Meal
import com.bruno.appservicio.features.MealDB.domain.repositories.MealsRepository
import java.net.UnknownHostException

class GetMealUseCase (
    private val repository: MealsRepository
) {


suspend operator fun invoke(): Result<List<Meal>> {
    return try {
        val meals = repository.getMeal()
        Result.success(meals)
        val filteredMeals = (meals ?: emptyList()).filter { it.name.isNotBlank() }

        if (filteredMeals.isEmpty()) {
            Result.failure(Exception("No meals found"))
        } else {
            Result.success(filteredMeals)
        }

    }catch (e: Exception){
        Result.failure(e)
    }
    }
}

