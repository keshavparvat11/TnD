package com.example.tnd.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tnd.screen.Dare.DareScreen
import com.example.tnd.screen.HomeScreen
import com.example.tnd.screen.Truth.TruthScreen

@Composable
fun TnDApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("truth") { TruthScreen() }
        composable("dare") { DareScreen() }
    }
}
