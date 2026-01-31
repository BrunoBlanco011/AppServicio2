package com.bruno.appservicio.features.MealDB.domain.repositories

import com.bruno.appservicio.features.MealDB.domain.entities.Meal

interface MealsRepository {
    suspend fun getMeal(): List<Meal>?

    suspend fun getMealDetails(id: String): Meal?
}


