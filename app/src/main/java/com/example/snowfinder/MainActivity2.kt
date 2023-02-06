package com.example.snowfinder

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.snowfinder.ui.theme.SnowFinderTheme
import com.example.snowfinder.ui.theme.TranslucentBlue
import com.example.snowfinder.utils.Constants
import com.example.snowfinder.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnowFinderTheme {
                val viewModel = hiltViewModel<WeatherViewModel>()
                val name = intent.getStringExtra("name")
                if (name != null) {
                    val coords = ResortCoords.resortMap[name]
                    val lat = coords?.get(0).toString()
                    val lon = coords?.get(1).toString()
                    viewModel.load(lat, lon, Constants.API_KEY)
                    var state = viewModel.weatherData.collectAsState(initial = null)
                    val weatherData = state

                    val innerData = weatherData.value?.data?.get(0)
                    val iconData = innerData?.weather

                    val context = LocalContext.current
                    val id = context.resources.getIdentifier("drawable/" + iconData?.icon, null,
                    context.packageName)


                    if (iconData != null) {
                        WeatherDisplay(name,
                            iconData.description,
                            innerData.temp.toString(),
                            innerData.app_temp.toString(),
                            innerData.snow.toString(), id)
                    } else {
                        Log.e("no work", "no worked")
                    }
                }
            }
        }
    }
}


@Composable
fun WeatherDisplay(name: String,
                   weatherDescription: String,
                   temperature: String,
                   appTemp: String,
                   snowFall: String,
                   weatherIcon: Int) {

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
                Text(text = name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(60.dp),
                modifier = Modifier
                    .padding(0.dp, 70.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()) {

                DateCard()


                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = TranslucentBlue)
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = weatherIcon),
                            contentDescription = "weathericon",
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                        Column(modifier = Modifier.wrapContentHeight()) {
                            Text(
                                text = "$temperature°C", color = Color.White, fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(0.dp)
                            )
                            Text(
                                text = weatherDescription, color = Color.White, fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                    }
                }

                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = TranslucentBlue)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Feels like $appTemp°C", color = Color.White, fontSize = 24.sp,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }

                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = TranslucentBlue)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Snowfall: $snowFall mm/hr", color = Color.White, fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun DateCard(){
    val calendar = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)
    val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = TranslucentBlue)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = dateFormat,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )
            Text(text = timeFormat,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SnowFinderTheme {
        val context = LocalContext.current

        val id = context.resources.getIdentifier("drawable/" + "a05n", null,
            context.packageName)
        WeatherDisplay("Talisman Mountain Resort, Haliburton",
            "Broken Clouds", "1", "2", "3", id)
    }
}