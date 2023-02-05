package com.example.snowfinder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.snowfinder.ui.theme.SnowFinderTheme
import com.example.snowfinder.ui.theme.TranslucentBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnowFinderTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content(){
    Background()
    Surface(color = Color.Black.copy(alpha = 0f),
        modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = TranslucentBlue)
            ) {
                Text(text = "SnowFinder",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp,
                    color = Color.White
                )
            }

            val resortData = ResortCoords().resortMap
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(0.dp, 50.dp).fillMaxHeight()) {
                for ((name) in resortData){
                    ResortButton(name = name)
                }
            }

        }
    }
}

@Composable
fun Background() {
    Box(modifier =  Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun ResortButton(name : String){
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(context, MainActivity2::class.java)
        intent.putExtra("name", name)
        context.startActivity(intent)
                     },
        colors = ButtonDefaults.buttonColors(backgroundColor = TranslucentBlue)) {
        Text(text = name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SnowFinderTheme {
        Content()
    }
}