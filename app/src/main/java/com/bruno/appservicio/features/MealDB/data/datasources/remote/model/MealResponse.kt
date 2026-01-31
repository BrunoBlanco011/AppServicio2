package com.bruno.appservicio.features.MealDB.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val results: List<MealsDto>
)

data class MealsDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String? = null
)