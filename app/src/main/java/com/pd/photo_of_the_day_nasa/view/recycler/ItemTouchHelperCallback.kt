package com.pd.photo_of_the_day_nasa.view.recycler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(private val adapter: RecyclerFragmentAdapter) :
    ItemTouchHelper.Callback() {

    override fun getMovementFlags( //в каком направлении двигаем элемент
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN // куда тащим вверх или вниз
        val swipeFlag =
            ItemTouchHelper.START or ItemTouchHelper.END // направление свайпа влево или вправо
        return makeMovementFlags(dragFlag, swipeFlag)// смешиваем флаги
    }

    override fun onMove( // соощаем нашему адаптеру о перемещении элемента
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) { // соощаем нашему адаптеру о удалении элемента
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
        itemViewHolder.onItemClear()
    }


}