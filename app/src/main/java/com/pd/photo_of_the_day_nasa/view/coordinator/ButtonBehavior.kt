package com.pd.photo_of_the_day_nasa.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonBehavior(context: Context, attr: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attr) { // передаем в  Behavior атрибуты
    //Behavior прикреплен в Layout

    override fun layoutDependsOn( // делаем зависимость от AppBarLayout
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout)
    }

    override fun onDependentViewChanged( // при изменении нашего AppBarLayout
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar = dependency as AppBarLayout
        val height = bar.height // делаем зависимость от высоты  AppBarLayout
        if (abs(bar.y) > (height / 2)) {
            child.visibility = View.GONE
        } else {
            child.visibility = View.VISIBLE
            child.alpha = (height / 2 - abs(bar.y)) / (height / 2) // делаем прозрачность
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}