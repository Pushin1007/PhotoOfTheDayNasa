package com.pd.photo_of_the_day_nasa.view.api_nasa

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    /*
     новый вью пейдер №2
     новый молодежный способ вместо устаревшего метода
     */

// пролистывание контента
    private val fragments = arrayOf(EarthFragment(), MarsFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]


}