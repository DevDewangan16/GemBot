package com.example.gembot.ui

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import io.noties.markwon.Markwon

@Composable
fun GemBotScreen(gemViewModel: GemViewModel) {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    val response by gemViewModel.response.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
        //.padding(16.dp)
    ) {
        //Text(text = "Ask Gemini AI", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Box (modifier = Modifier
                .height(600.dp)
                .weight(1f) // ✅ Ensures TextField stays at the bottom even when empty
                .fillMaxWidth()
                .padding(8.dp)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item{
                        MarkdownText(response = response)//used for the beautification of the response
//                        Text(
//                            text = "Response: $response",
//                            style = MaterialTheme.typography.bodyLarge,
//                        )
//                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                            Button(onClick = {
//
//                            },
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Color(0xFF2F2F2F)
//                                )) {
//                                Text(text = "Save")
//                            }
//                        }
                    }
                }
            }

            OutlinedTextField(
                value = userInput, onValueChange = { userInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    // .weight(1f)
                    .padding(start = 3.dp, end = 3.dp, bottom = 17.dp)
                ,placeholder = { Text("Ask Gemini...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEDEF6),
                    unfocusedContainerColor = Color(0xFFEEDEF6),
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        gemViewModel.fetchResponse(userInput.text)
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = " ")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MarkdownText(response: String) {
    val context = LocalContext.current
    val markwon = remember { Markwon.create(context) }
    val spanned = remember(response) { markwon.toMarkdown(response) }

    AndroidView(
        factory = { TextView(it).apply { movementMethod = LinkMovementMethod.getInstance() } },
        update = { it.text = spanned }
    )
}
