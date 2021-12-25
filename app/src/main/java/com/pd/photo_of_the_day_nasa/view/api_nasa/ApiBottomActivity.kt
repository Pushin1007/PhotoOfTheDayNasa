package com.pd.photo_of_the_day_nasa.view.api_nasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.badge.BadgeDrawable
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.ActivityBottomApiBinding

class ApiBottomActivity : AppCompatActivity() {
    lateinit var binding: ActivityBottomApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener { //обрабатываем нажатия на bottomNavigationView
            when (it.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment()).commit()
                    true //true для кликабельности кнопки меню
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }




        binding.bottomNavigationView.selectedItemId =
            R.id.bottom_view_mars // по умолчанию будет нажата кнопка земля

        //Настройки  bottomNavigationView, поэкспериментируем с землей

        // индексы над кнопкой, например количество непрочитанных сообщений
        val badge =
            binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_earth)// ссылка на  Badge
        badge.number = 7 // индекс - например сообщения, ничего не значит, исключительно для обучения
        badge.badgeGravity = BadgeDrawable.TOP_END // расположения индекса
        badge.maxCharacterCount = 3 // максимальное количество символов в индексе
    }


}