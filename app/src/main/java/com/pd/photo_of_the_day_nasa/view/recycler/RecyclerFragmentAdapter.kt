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
    private val data: MutableList<Data>,
    private val callbackListener: MyCallback
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1) // обновляем адаптер точечно
    }

    private fun generateItem(): Data {
        return Data(label = "Buy") // TODO написать добавление нового фрагмента
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
        return data[position].type
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])//вызывается абстрактный метод, и вызывается нужная реализация
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class TodoViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecycleItemTodoBinding.bind(itemView).apply {
                label.text = data.label
                descriptionTextView.text = data.description
                todoImageView.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }

    inner class BuyViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecycleItemBuyBinding.bind(itemView).apply {
                label.text = data.label
                root.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
                addItemImageView.setOnClickListener {// добавляем элемент
                    addItemToPosition()
                }
                removeItemImageView.setOnClickListener { // удаляем элемент
                    removeItem()
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
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecycleItemHeaderBinding.bind(itemView).apply {
                header.text = data.label
                header.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }


}