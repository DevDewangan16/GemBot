package com.example.gembot.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class GemAPPScreen {
    Login,
    Home,
    Text,
    Image
}
@Composable
fun GemApp(gemViewModel: GemViewModel= viewModel(),
           navHostController: NavHostController= rememberNavController()){
    NavHost(navController = navHostController, startDestination = GemAPPScreen.Login.name) {
        composable(route=GemAPPScreen.Login.name){
            LoginScreen(navHostController = navHostController)
        }
        composable(route = GemAPPScreen.Home.name){
            HomeScreen(navHostController)
        }
        composable(route = GemAPPScreen.Text.name){
            GemBotScreen(gemViewModel = gemViewModel)
        }
        composable(route = GemAPPScreen.Image.name){
            GemBotImageScreen(gemViewModel = gemViewModel)
        }
    }
}