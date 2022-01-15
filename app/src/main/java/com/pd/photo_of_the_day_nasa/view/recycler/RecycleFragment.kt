package com.pd.photo_of_the_day_nasa.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.pd.photo_of_the_day_nasa.*

import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleBinding
import com.pd.photo_of_the_day_nasa.view.MainActivity


class RecycleFragment : Fragment() {


    private var _binding: FragmentRecycleBinding? = null
    val binding: FragmentRecycleBinding
        get() {
            return _binding!!
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecycleBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf(
            Data("TODO", "04/01/2022", TYPE_TODO) to false,
            Data("TODO", "05/01/2022", TYPE_TODO) to false,
            Data("TODO", "05/01/2022", TYPE_TODO) to false,
            Data("BUY", "dddddddddd", TYPE_BUY) to false,
            Data("TODO", "05/01/2022", TYPE_TODO) to false,
            Data("TODO", "05/01/2022", TYPE_TODO) to false,
            Data("BUY", "", TYPE_BUY) to false
        )
// добавляем заголовок
        data.add(0, Data("Дела и покупки", "", type = TYPE_HEADER, 20000000) to false)

        val adapter = RecyclerFragmentAdapter(data,
            object : MyCallback {
                override fun onClick(position: Int) {
//                    Toast.makeText(
//                        context,
//                        "РАБОТАЕТ ${data[position].first.label} ${data[position].first.description}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    (requireActivity() as MainActivity).supportFragmentManager
                        .beginTransaction().add(R.id.container, AddBuyFragment.newInstance())
                        .addToBackStack("").commit()

                    parentFragmentManager.setFragmentResultListener(
                        KEY_BUY_RESULT_ADD,
                        viewLifecycleOwner, { requestKey, result ->
                            val newItemBuy = result.getParcelable<Data>(ARG_BUY_ADD)

                            data.add(
                                Data(
                                    newItemBuy?.label,
                                    newItemBuy?.description,
                                    TYPE_BUY
                                ) to false
                            )
                        })

                }

            })




        binding.recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView) //запускаем код
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RecycleFragment()
    }


}