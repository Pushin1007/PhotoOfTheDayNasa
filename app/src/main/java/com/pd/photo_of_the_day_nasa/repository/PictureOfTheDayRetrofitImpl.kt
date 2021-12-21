package com.pd.photo_of_the_day_nasa.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl { // реализация интерфейса
//    private val baseUrl = "https://api.nasa.gov/"
//    fun getRetrofitImpl(): PictureOfTheDayAPI { // реализация интерфейса
//        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .build() //Gson конвертируем в наш data class
//        return retrofit.create(PictureOfTheDayAPI::class.java)
//    }
private val api by lazy {
    val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
    retrofit.create(PictureOfTheDayAPI::class.java)
}

    private val baseUrl = "https://api.nasa.gov/" // корень запроса
    fun getRetrofitImpl():PictureOfTheDayAPI{
        return api
    }
}