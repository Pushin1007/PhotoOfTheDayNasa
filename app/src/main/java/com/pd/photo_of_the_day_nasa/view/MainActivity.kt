package com.pd.photo_of_the_day_nasa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pd.photo_of_the_day_nasa.NUMBER_OF_THEME
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.view.picture.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = Bundle()
        val numberOfTheme = bundle.getInt(NUMBER_OF_THEME)

        when (numberOfTheme) {
            1 -> {
                setTheme(R.style.Grey)
                recreate()
            }
            2 -> {
                setTheme(R.style.Purple)
                recreate()
            }
        }
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }

    }


}

