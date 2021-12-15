package com.pd.photo_of_the_day_nasa.repository

import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseData(
    //обзываем псевдонимами для удобства использования
    @field:SerializedName("copyright") val copyright: String?,
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("title") val title: String?, // нужно, это описание
    @field:SerializedName("url") val url: String?, // нужно, это сама картинка
    @field:SerializedName("hdurl") val hdurl: String?

)