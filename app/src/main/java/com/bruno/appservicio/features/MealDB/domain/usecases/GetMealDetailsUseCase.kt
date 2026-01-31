package com.bruno.appservicio.features.MealDB.domain.usecases

import com.bruno.appservicio.features.MealDB.domain.entities.Meal
import com.bruno.appservicio.features.MealDB.domain.repositories.MealsRepository

class GetMealDetailsUseCase(
    private val repository: MealsRepository
)
{
    suspend operator fun invoke(id: String): Result<Meal> {

    return try {
    val meal = repository.getMealDetails(id)
        if (meal != null) {
            Result.success(meal)
        } else {
            Result.failure(Exception("Meal not found"))
    }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
