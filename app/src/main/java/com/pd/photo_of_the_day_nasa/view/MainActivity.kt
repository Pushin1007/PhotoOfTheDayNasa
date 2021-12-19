package com.pd.photo_of_the_day_nasa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }
    }
}