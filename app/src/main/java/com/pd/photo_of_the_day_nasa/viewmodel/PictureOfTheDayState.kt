package com.pd.photo_of_the_day_nasa.viewmodel

import com.pd.photo_of_the_day_nasa.repository.EarthEpicServerResponseData
import com.pd.photo_of_the_day_nasa.repository.MarsPhotosServerResponseData
import com.pd.photo_of_the_day_nasa.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayState { // запечатанный класс с состояниями
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData) :
        PictureOfTheDayState()

    data class SuccessEarthEpic(val pictureOfTheDayResponseData: List<EarthEpicServerResponseData>) :
        PictureOfTheDayState()

    //    data class Loading(val progress: Int?) : PictureOfTheDayState()
    data class Error(val error: Throwable) : PictureOfTheDayState()
    object Loading : PictureOfTheDayState()

    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) :
        PictureOfTheDayState()
}