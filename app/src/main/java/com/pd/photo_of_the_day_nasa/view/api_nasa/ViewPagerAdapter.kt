package com.pd.photo_of_the_day_nasa.view.api_nasa

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) { // новый вью пейдер №2

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), MoonFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}