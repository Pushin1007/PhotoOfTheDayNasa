package com.pd.photo_of_the_day_nasa.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pd.photo_of_the_day_nasa.BuildConfig
import com.pd.photo_of_the_day_nasa.repository.PictureOfTheDayResponseData
import com.pd.photo_of_the_day_nasa.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayState> = MutableLiveData(), //создаем нужные экземпляры
    private val retrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl(),
) : ViewModel() {
    fun getData(): LiveData<PictureOfTheDayState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() { // запрос  без даты
        liveDataForViewToObserve.value = PictureOfTheDayState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey)
                .enqueue(callback)// делаем  асинхронный запрос
        }
    }

    fun sendServerRequest(date:String) { // запрос с датой
        liveDataForViewToObserve.value = PictureOfTheDayState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey,date).enqueue(callback)
        }
    }


    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value = PictureOfTheDayState.Success(response.body()!!)
            } else {
                liveDataForViewToObserve.postValue(PictureOfTheDayState.Error(error("Error")))// возможно написал какую-то дичь?
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            t.printStackTrace()
            liveDataForViewToObserve.postValue(PictureOfTheDayState.Error(t))
        }

    }


}