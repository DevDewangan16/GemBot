package com.example.gembot.ui

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gembot.BuildConfig
import com.example.gembot.ui.data.ChatMessage
import com.example.gembot.ui.data.Content
import com.example.gembot.ui.data.Content1
import com.example.gembot.ui.data.GeminiImageRequest
import com.example.gembot.ui.data.GeminiRequest
import com.example.gembot.ui.data.InlineData
import com.example.gembot.ui.data.Part
import com.example.gembot.ui.data.Part1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GemViewModel(application: Application): AndroidViewModel(application) {

    private val apiKey = BuildConfig.GEMINI_API_KEY

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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun processImage(bitmap: Bitmap, apiKey: String, userQuery: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _chatHistory.value += ChatMessage(text = userQuery, isQuestion = true)

                val base64Image = ImageUtils.convertBitmapToBase64(bitmap)

                val request = GeminiImageRequest(
                    contents = listOf(
                        Content1(
                            parts = listOf(
                                Part1(text = userQuery),
                                Part1(
                                    inlineData = InlineData(
                                        mimeType = "image/jpeg",
                                        data = base64Image
                                    )
                                )
                            )
                        )
                    )
                )

                val response = withContext(Dispatchers.IO) {
                    RetrofitClient1.instance.processImage(apiKey, request)
                }

                val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "No text extracted"
                _chatHistory.value += ChatMessage(text = responseText, isQuestion = false)
            } catch (e: Exception) {
                _chatHistory.value += ChatMessage(text = "Error: ${e.message}", isQuestion = false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}