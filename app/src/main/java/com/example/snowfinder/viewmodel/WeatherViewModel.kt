package com.example.snowfinder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snowfinder.api.ApiService
import com.example.snowfinder.model.Data
import com.example.snowfinder.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val service: ApiService): ViewModel(){

    val weatherData = MutableStateFlow<Weather?>(null)
    fun load(lat: String, lon: String, key: String){
        viewModelScope.launch{
            val weatherResp = service.getWeather(lat, lon, key)
            if (weatherResp.isSuccessful){
                weatherData.value = weatherResp.body()
            } else{
                Log.e("BRK_ViewModel", "error, weatherdata call failed: ${weatherResp.body()}")
            }

        }
    }
}