package com.example.gembot.ui

import com.example.gembot.ui.data.GeminiImageRequest
import com.example.gembot.ui.data.GeminiImageResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService1 {
    @POST("v1beta/models/gemini-1.5-pro:generateContent")
    suspend fun processImage(
        @Query("key") apiKey: String,
        @Body request: GeminiImageRequest
    ): GeminiImageResponse
}