package com.pd.photo_of_the_day_nasa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pd.photo_of_the_day_nasa.*
import com.pd.photo_of_the_day_nasa.databinding.ActivityMainBinding
import com.pd.photo_of_the_day_nasa.view.picture.PictureOfTheDayFragment



class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeOne -> R.style.Grey
            ThemeSecond -> R.style.Purple
            else -> 0
        }
    }
}





