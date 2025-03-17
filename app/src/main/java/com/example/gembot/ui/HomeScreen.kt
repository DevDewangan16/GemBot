package com.example.gembot.ui
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.gembot.R


@Composable
fun HomeScreen(navHostController: NavHostController){
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.home, imageLoader),
            contentDescription = null,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(text = "GemBot",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            fontWeight = FontWeight.Bold,
            )
        Text(text = "Your AI-powered assistant",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp))
        Text(text = "What can GemBot do?",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(text = "1.Answer questions and provide information",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        )
        Text(text = "2.Analyze images and provide descriptions",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        )
        Text(text = "3.Assist with creative and analytical tasks",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Card (modifier = Modifier
                .size(150.dp)
                .clickable {
                    navHostController.navigate(GemAPPScreen.Text.name)
                }){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Start Text Chat",
                        fontWeight = FontWeight.Bold)
                }
            }
            Card (modifier = Modifier
                .size(150.dp)
                .clickable {
                    navHostController.navigate(GemAPPScreen.Image.name)
                }){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Start Image Chat",
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}