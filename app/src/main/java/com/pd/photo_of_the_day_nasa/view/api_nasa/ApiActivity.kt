package com.pd.photo_of_the_day_nasa.view.api_nasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {
    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                }
            }).attach()

        setCustomTabs()
        /*
        Вариант попроще с обычными кнопками в виде картинок бес спец лайаутов
                binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_moon)
         */

    }


    private fun setCustomTabs() {// Делаем  кастомные вкладки в TabLayout
        // подключаем созданные  Layout с  картинкой  и текстом
        binding.tabLayout.getTabAt(0)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tabitem_earth, null)
        binding.tabLayout.getTabAt(1)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tabitem_mars, null)
        binding.tabLayout.getTabAt(2)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tabitem_moon, null)
    }

}