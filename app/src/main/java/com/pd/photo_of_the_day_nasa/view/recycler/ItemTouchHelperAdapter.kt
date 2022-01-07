package com.pd.photo_of_the_day_nasa.view.recycler

interface ItemTouchHelperAdapter { // интерфейс где будем улавливать магипуляции с нашими элементами
    fun onItemMove(fromPosition: Int, toPosition: Int) // перемещение из позиции в позицию
    fun onItemDismiss(position: Int) // удаление элемента
}

interface ItemTouchHelperViewHolder {  // интерфейс который отвечает за визуализацию выбора элемента
    fun onItemSelected()
    fun onItemClear()
}