package com.example.gembot.ui

import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gembot.BuildConfig

@Composable
fun GemBotImageScreen(gemViewModel: GemViewModel) {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    val apiKey = BuildConfig.GEMINI_API_KEY
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display Image if Selected
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .weight(1f) // ✅ Ensures TextField stays at the bottom even when empty
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(
                            text = gemViewModel.extractedText.value,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
//            Card(
//                modifier = Modifier
//                    .padding(start = 3.dp, end = 3.dp, bottom = 40.dp)
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .clickable {
//                        launcher.launch("image/*")
//                    },
//                colors = CardDefaults.cardColors(
//                    containerColor = Color(0xFFCCE5E3)
//                )
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    //  horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    Box(modifier = Modifier
//                        .weight(3.5f)
//                        .fillMaxHeight(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "Upload an Image",
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                    IconButton(
//                        modifier = Modifier.weight(0.5f),
//                        onClick = {
//                            bitmap?.let { gemViewModel.processImage(it, apiKey) }//used to target or call the IMAGETOTEXT response
//                        },
//                        enabled = bitmap != null
//                    ) {
//                        Icon(imageVector = Icons.Default.Send, contentDescription = " ")
//                    }
//                }
//            }
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 17.dp),
                placeholder = { Text("Ask Gemini...") },
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
                leadingIcon = {
                    IconButton(onClick = {
                        launcher.launch("image/*")
                    }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Upload")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        bitmap?.let { gemViewModel.processImage(it, apiKey) }
                        userInput = TextFieldValue("") // Clear the input field
                    },enabled = bitmap!=null) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
                    }
                }
            )
        }

    }
    Spacer(modifier = Modifier.height(16.dp))
}

