package com.pd.photo_of_the_day_nasa.repository

data class EarthEpicServerResponseData(
    val identifier: String,
    val caption: String,
    val image: String,
    val version: String,
    val date: String,
)