package com.example.gembot

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gembot.ui.GemApp
import com.example.gembot.ui.GemViewModel
import com.example.gembot.ui.theme.GemBotTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GemBotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    GemApp()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GreetingPreview() {
    GemBotTheme {
        GemApp()
    }
}