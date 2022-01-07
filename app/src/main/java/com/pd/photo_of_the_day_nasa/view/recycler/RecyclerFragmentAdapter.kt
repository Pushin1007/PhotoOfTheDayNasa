package com.pd.photo_of_the_day_nasa.view.recycler

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.pd.photo_of_the_day_nasa.TYPE_HEADER
import com.pd.photo_of_the_day_nasa.TYPE_TODO
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemBuyBinding
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemHeaderBinding
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemTodoBinding

class RecyclerFragmentAdapter(
    private val data: MutableList<Pair<Data, Boolean>>,
    private val callbackListener: MyCallback
) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

//    fun appendItem() {
//        data.add(generateItem())
//        notifyItemInserted(itemCount - 1) // обновляем адаптер точечно
//    }

    private fun generateItem(): Pair<Data, Boolean> { // TODO написать добавление нового фрагмента
        return Data(label = "Buy") to false
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder { // метод в котором генерируются элементы

        return when (viewType) {
            TYPE_TODO -> {
                val bindingViewHolder = FragmentRecycleItemTodoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TodoViewHolder(bindingViewHolder.root)
            }
            TYPE_HEADER -> {
                val bindingViewHolder = FragmentRecycleItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(bindingViewHolder.root)
            }
            else -> {
                val bindingViewHolder = FragmentRecycleItemBuyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BuyViewHolder(bindingViewHolder.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int { // определяем тип ячейки
        return data[position].first.type
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])//вызывается абстрактный метод, и вызывается нужная реализация
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class TodoViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecycleItemTodoBinding.bind(itemView).apply {
                label.text = data.first.label
                descriptionTextView.text = data.first.description
                todoImageView.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }

    inner class BuyViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecycleItemBuyBinding.bind(itemView).apply {
                label.text = data.first.label
                root.setOnClickListener { // по клику на вест элемент просто показываем тост
                    callbackListener.onClick(layoutPosition)
                }
                addItemImageView.setOnClickListener {// добавляем элемент
                    addItemToPosition()
                }
                removeItemImageView.setOnClickListener { // удаляем элемент
                    removeItem()
                }
                moveItemDown.setOnClickListener { // двигаем элемент вверх
                    moveDown()
                }
                moveItemUp.setOnClickListener { // двигаем элемент вниз
                    moveUp()
                }
                // по клику на ярлык ячейки показываем внутренности
                buyDescriptionTextView.visibility =
                    if (data.second) View.VISIBLE else View.GONE // показываем вьюху
                label.setOnClickListener {
                    toggleDescription()
                }

            }
        }

        private fun addItemToPosition() { //добавление нового элемента Buy
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition) // обновление только вставленного элеманта
        }

        private fun removeItem() { // удаление элемента Buy
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() { // функция движения вверх
            if (layoutPosition > 1) {
                data.removeAt(layoutPosition).apply {
                    data.add(layoutPosition - 1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }


        }

        private fun moveDown() { // функция движения вниз
            if (layoutPosition < getItemCount() - 1) {
                data.removeAt(layoutPosition).apply {
                    data.add(layoutPosition + 1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }

        private fun toggleDescription() { // показываем внутренности ячейки
            data[layoutPosition] = data[layoutPosition].run {
                first to !second
            }
            notifyItemChanged(layoutPosition) //обновляем точесно элемент
        }

        override fun onItemSelected() { // реализуем метод выделения
            itemView.setBackgroundColor(Color.GRAY)
        }

        override fun onItemClear() { // реализуем метод снятия выделения
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecycleItemHeaderBinding.bind(itemView).apply {
                header.text = data.first.label
                header.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) { // реализуем метод перемещения
        data.removeAt(fromPosition).apply {
            data.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) { // реализуем метод  удаления элементы
        data.removeAt(position)
        notifyItemRemoved(position)
    }


}