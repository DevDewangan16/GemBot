package com.example.gembot.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GemApp(gemViewModel: GemViewModel= viewModel()){
//    GemBotScreen(gemViewModel = gemViewModel)
    GemBotImageScreen(gemViewModel = gemViewModel)
}