package com.pd.photo_of_the_day_nasa.utils


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

// кастомная view для отображения квадратной картинки
class CustomImageView @JvmOverloads constructor( //@JvmOverloads - нужен для генерации нескольких конструкторов
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) :
    AppCompatImageView(context, attrs, style) {

    //переопределяем его метод измерений
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}