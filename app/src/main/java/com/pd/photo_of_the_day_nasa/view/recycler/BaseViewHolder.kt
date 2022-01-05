package com.pd.photo_of_the_day_nasa.view.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View) :
    RecyclerView.ViewHolder(view) { //базовый предок для холдеров
    //    abstract fun bind(data: Pair<Data, Boolean>)
    abstract fun bind(data: Data)
}