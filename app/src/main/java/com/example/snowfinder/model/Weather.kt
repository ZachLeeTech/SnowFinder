package com.example.snowfinder.model

data class Weather(
    val count: Int,
    val `data`: List<Data>,
    val minutely: List<String>
)