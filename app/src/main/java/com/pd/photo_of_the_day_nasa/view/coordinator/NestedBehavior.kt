package com.pd.photo_of_the_day_nasa.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class NestedBehavior(context: Context? = null, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

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

        val bar = dependency as AppBarLayout // делаем зависимость от высоты  AppBarLayout
        child.y = bar.height + bar.y //нестедскрол едет вместе с аппбарром
        return super.onDependentViewChanged(parent, child, dependency)
    }
}