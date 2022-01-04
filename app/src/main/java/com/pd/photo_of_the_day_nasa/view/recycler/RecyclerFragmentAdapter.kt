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
import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleItemTodoBinding

class RecyclerFragmentAdapter(private val data: List<Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_TODO -> {
                val bindingViewHolder = FragmentRecycleItemTodoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TodoViewHolder(bindingViewHolder.root)
            }
//            TYPE_HEADER -> {
//                val bindingViewHolder = FragmentRecycleItemHeaderBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//                HeaderViewHolder(bindingViewHolder.root)
//            }
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
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        override fun bind(data: Pair<Data, Boolean>) {
//            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
//                someTextTextView.text = data.first.someText
//                descriptionTextView.text = data.first.someDescription
//                wikiImageView.setOnClickListener {
//                    callbackListener.onClick(layoutPosition)
//                }
//            }
//        }
    }

    inner class BuyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        override fun bind(data: Pair<Data, Boolean>) {
//            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
//                someTextTextView.text = data.first.someText
//                marsImageView.setOnClickListener {
//                    callbackListener.onClick(layoutPosition)
//                }
//                addItemImageView.setOnClickListener {
//                    addItemToPosition()
//                }
//                removeItemImageView.setOnClickListener {
//                    removeItem()
//                }
//                moveItemDown.setOnClickListener {
//                    moveDown()
//                }
//                moveItemUp.setOnClickListener {
//                    moveUp()
//                }
//                marsDescriptionTextView.visibility = if(data.second) View.VISIBLE else View.GONE
//                someTextTextView.setOnClickListener {
//                    toggleDescription()
//                }
//
//                dragHandleImageView.setOnTouchListener{v, event->
//                    Log.d("mylogs","setOnTouchListener $event")
//                    if(MotionEventCompat.getActionMasked(event)== MotionEvent.ACTION_DOWN){ // TODO This method will be removed in a future release.
//                        onStartDragListener.onStartDrag(this@MarsViewHolder)
//                    }
//                    false
//                }
//            }
//        }
//
//        private fun toggleDescription() {
//            data[layoutPosition] = data[layoutPosition].run {
//                first to !second
//            }
//            notifyItemChanged(layoutPosition)
//        }
//
//        private fun moveUp() { // FIXME ДЗ убрать ошиюбку java.lang.IndexOutOfBoundsException
//            data.removeAt(layoutPosition).apply {
//                data.add(layoutPosition - 1, this)
//            }
//            notifyItemMoved(layoutPosition, layoutPosition - 1)
//        }
//
//        private fun moveDown() { // FIXME ДЗ убрать ошиюбку java.lang.IndexOutOfBoundsException
//            data.removeAt(layoutPosition).apply {
//                data.add(layoutPosition + 1, this)
//            }
//            notifyItemMoved(layoutPosition, layoutPosition + 1)
//        }
//
//        private fun addItemToPosition() {
//            data.add(layoutPosition, generateItem())
//            notifyItemInserted(layoutPosition)
//        }
//
//        private fun removeItem() {
//            data.removeAt(layoutPosition)
//            notifyItemRemoved(layoutPosition)
//        }
//
//        override fun onItemSelected() {
//            itemView.setBackgroundColor(Color.CYAN)
//        }
//
//        override fun onItemClear() {
//            itemView.setBackgroundColor(0)
//        }


    }

}