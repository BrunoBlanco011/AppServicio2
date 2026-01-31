package com.bruno.appservicio.features.MealDB.data.datasources.remote.mapper

import com.bruno.appservicio.features.MealDB.data.datasources.remote.model.MealsDto
import com.bruno.appservicio.features.MealDB.domain.entities.Meal


fun MealsDto.toDomain(): Meal {

    return Meal(
        id = this.idMeal,
        name = this.strMeal,
        imageUrl = this.strMealThumb,
        instructions = this.strInstructions?: "No hay instrucciones"

    )
}