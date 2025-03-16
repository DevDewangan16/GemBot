package com.example.gembot.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gembot.ui.data.Content
import com.example.gembot.ui.data.GeminiRequest
import com.example.gembot.ui.data.Part
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GemViewModel(application: Application):AndroidViewModel(application) {
    private val apiKey = "AIzaSyDbGhLvg47UU1tY7O0LS7dbeho1dFEuvPk"

    private val _response = MutableStateFlow("Ask something...")
    val response = _response.asStateFlow()

    fun fetchResponse(prompt: String) {
        viewModelScope.launch {
            try {
                val request = GeminiRequest(
                    contents = listOf(
                        Content(parts = listOf(Part(text = prompt)))  // ✅ Correct format
                    )
                )

                val result = RetrofitClient.instance.getGeminiResponse(apiKey, request)

                // ✅ Correctly extracting response text
                val responseText = result.candidates?.getOrNull(0)?.content?.parts?.getOrNull(0)?.text ?: "No response"

                _response.value = responseText

//                val newEntry = RequestResponse(prompt,responseText)
//                _historyList.value = _historyList.value + newEntry


            } catch (e: Exception) {
                _response.value = "Error: ${e.message}"
            }
        }
    }
}