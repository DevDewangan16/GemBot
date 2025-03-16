package com.example.gembot.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gembot.ui.data.ChatMessage
import com.example.gembot.ui.data.Content
import com.example.gembot.ui.data.GeminiRequest
import com.example.gembot.ui.data.Part
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GemViewModel(application: Application): AndroidViewModel(application) {
    private val apiKey = "AIzaSyCenrXarZ6Ar04IQC-KCi45wmS267gUTpo"

    private val _chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatHistory = _chatHistory.asStateFlow()

    fun fetchResponse(prompt: String) {
        viewModelScope.launch {
            try {
                // Add the user's question to the chat history
                _chatHistory.value += ChatMessage(text = prompt, isQuestion = true)

                val request = GeminiRequest(
                    contents = listOf(
                        Content(parts = listOf(Part(text = prompt)))
                    )
                )

                val result = RetrofitClient.instance.getGeminiResponse(apiKey, request)

                // Extract the response text
                val responseText = result.candidates?.getOrNull(0)?.content?.parts?.getOrNull(0)?.text ?: "No response"

                // Add the response to the chat history
                _chatHistory.value += ChatMessage(text = responseText, isQuestion = false)

            } catch (e: Exception) {
                // Add the error message to the chat history
                _chatHistory.value += ChatMessage(text = "Error: ${e.message}", isQuestion = false)
            }
        }
    }
}