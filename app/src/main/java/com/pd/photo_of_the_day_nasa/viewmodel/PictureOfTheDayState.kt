package com.pd.photo_of_the_day_nasa.viewmodel

import com.pd.photo_of_the_day_nasa.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayState { // запечатанный класс с состояниями
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):PictureOfTheDayState()
    data class Loading(val progress: Int?):PictureOfTheDayState()
    data class Error(val error: Throwable):PictureOfTheDayState()
}