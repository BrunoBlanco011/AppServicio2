package com.bruno.appservicio.features.MealDB.data.repositories

import com.bruno.appservicio.core.network.MealDBApi
import com.bruno.appservicio.features.MealDB.data.datasources.remote.mapper.toDomain
import com.bruno.appservicio.features.MealDB.domain.entities.Meal
import com.bruno.appservicio.features.MealDB.domain.repositories.MealsRepository

class MealRepositoryImpl(
    private val api: MealDBApi
) : MealsRepository {

    override suspend fun getMeal(): List<Meal>? {
        val response = api.getMeal()
        return response.results?.map { it.toDomain() }

    }

    override suspend fun getMealDetails(id: String): Meal?{
        val response = api.getMealDetails(id)
        return response.results?.firstOrNull()?.toDomain()

    }
}