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
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment()).commit()
                    true
                }
                R.id.bottom_view_moon -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MoonFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_mars)
        badge.number = 120
        badge.badgeGravity = BadgeDrawable.TOP_END
        badge.maxCharacterCount = 3
    }


}