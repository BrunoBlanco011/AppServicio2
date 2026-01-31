package com.bruno.appservicio.core.network

import com.bruno.appservicio.features.MealDB.data.datasources.remote.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBApi {
    @GET("search.php?f=a")
    suspend fun getMeal(): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): MealResponse


}


