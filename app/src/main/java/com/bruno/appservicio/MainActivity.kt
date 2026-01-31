package com.bruno.appservicio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bruno.appservicio.core.di.AppContainer
import com.bruno.appservicio.core.theme.AppServicioTheme
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealDetailsUseCase
import com.bruno.appservicio.features.MealDB.domain.usecases.GetMealUseCase
import com.bruno.appservicio.features.MealDB.presentation.screens.MealDetailScreen
import com.bruno.appservicio.features.MealDB.presentation.screens.MealScreen
import com.bruno.appservicio.features.MealDB.presentation.viewmodels.MealViewModel
import com.bruno.appservicio.features.MealDB.presentation.viewmodels.MealViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)

        val getMealUseCase = GetMealUseCase(appContainer.mealsRepository)
        val getMealDetailsUseCase = GetMealDetailsUseCase(appContainer.mealsRepository)

        val mealViewModelFactory = MealViewModelFactory(
            getMealUseCase = getMealUseCase,
            getMealDetailsUseCase = getMealDetailsUseCase
        )

        enableEdgeToEdge()
        setContent {
            AppServicioTheme {
                val navController = rememberNavController()
                val viewModel: MealViewModel = viewModel(factory = mealViewModelFactory)

                NavHost(navController = navController, startDestination = "meal_list") {
                    composable("meal_list") {
                        MealScreen(
                            viewModel = viewModel,
                            onMealClick = { meal ->
                                viewModel.selectMeal(meal)
                                navController.navigate("meal_detail")
                            }
                        )
                    }
                    composable("meal_detail") {
                        MealDetailScreen(
                            viewModel = viewModel,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
