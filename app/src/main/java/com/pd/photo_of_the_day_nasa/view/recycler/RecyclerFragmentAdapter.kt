package com.pd.photo_of_the_day_nasa.view.recycler

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemBuyBinding
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemHeaderBinding
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemTodoBinding

class RecyclerFragmentAdapter(
    private val data: List<Data>,
    private val callbackListener: MyCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder { // метод в котором генерируются элементы

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

    override fun getItemViewType(position: Int): Int { // определяем тип
        return data[position].type
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (getItemViewType(position)) {
            TYPE_TODO -> {
                (holder as TodoViewHolder).bind(data[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(data[position])
            }
            else -> {
                (holder as BuyViewHolder).bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecycleItemTodoBinding.bind(itemView).apply {
                label.text = data.label
                descriptionTextView.text = data.description
                todoImageView.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }

    inner class BuyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecycleItemBuyBinding.bind(itemView).apply {
                label.text = data.label
                root.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecycleItemHeaderBinding.bind(itemView).apply {
                header.text = data.label
                header.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
            }
        }
    }


}