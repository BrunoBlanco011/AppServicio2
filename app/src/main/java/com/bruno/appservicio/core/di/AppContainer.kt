package com.bruno.appservicio.core.di

import android.content.Context
import com.bruno.appservicio.core.network.MealDBApi
import com.bruno.appservicio.features.MealDB.data.repositories.MealRepositoryImpl
import com.bruno.appservicio.features.MealDB.domain.repositories.MealsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mealDBApi: MealDBApi by lazy {
        retrofit.create(MealDBApi::class.java)
    }

    val mealsRepository: MealsRepository by lazy {
        MealRepositoryImpl(mealDBApi)
    }
}
