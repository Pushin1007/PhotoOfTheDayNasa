package com.pd.photo_of_the_day_nasa.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class NestedBehavior(context: Context?=null, attr: AttributeSet?=null): CoordinatorLayout.Behavior<View>(context,attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar  = dependency as AppBarLayout
        child.y =bar.height+bar.y
        //Log.d("mylogs","${child.y} child.y")

        return super.onDependentViewChanged(parent, child, dependency)
    }
}