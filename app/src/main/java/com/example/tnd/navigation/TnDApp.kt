package com.example.tnd.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tnd.screen.Dare.DareScreen
import com.example.tnd.screen.Dare.ShowDare
import com.example.tnd.screen.FeedbackScreen
import com.example.tnd.screen.HomeScreen
import com.example.tnd.screen.PlayerInputScreen
import com.example.tnd.screen.SpinBottle.SpinBottleScreen
import com.example.tnd.screen.Truth.ShowTruth
import com.example.tnd.screen.Truth.TruthScreen

@Composable
fun TnDApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("truth") { TruthScreen(navController) }
        composable("dare") { DareScreen(navController) }
        composable("Feedback") { FeedbackScreen(navController) }
        composable("spin") { PlayerInputScreen(navController) }

        // --- Dares ---
        composable(
            route = "ShowDare/{topic}",
            arguments = listOf(navArgument("topic") { type = NavType.StringType })
        ) { backStackEntry ->
            val topic = backStackEntry.arguments?.getString("topic") ?: "Unknown"
            ShowDare(navController, topic)
        }

        // --- Spin Bottle / Game ---
        composable(
            route = "GameScreen/{numberOfPlayers}",
            arguments = listOf(navArgument("numberOfPlayers") { type = NavType.IntType })
        ) { backStackEntry ->
            val numberOfPlayers = backStackEntry.arguments?.getInt("numberOfPlayers") ?: 2
            SpinBottleScreen(navController, numberOfPlayers)
        }

        // --- Truth ---
        composable(
            route = "ShowTruth/{topic}",
            arguments = listOf(navArgument("topic") { type = NavType.StringType })
        ) { backStackEntry ->
            val topic = backStackEntry.arguments?.getString("topic") ?: "Unknown"
            ShowTruth(navController, topic)
        }
    }
}
