package com.pd.photo_of_the_day_nasa.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pd.photo_of_the_day_nasa.TYPE_BUY
import com.pd.photo_of_the_day_nasa.TYPE_HEADER
import com.pd.photo_of_the_day_nasa.TYPE_TODO

import com.pd.photo_of_the_day_nasa.databinding.FragmentRecycleBinding


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
            Data("TODO", "04.01.2022", type = TYPE_TODO) to false,
            Data("TODO", "05.01.2022", type = TYPE_TODO) to false,
            Data("TODO", "05.01.2022", type = TYPE_TODO) to false,
            Data("BUY", "", type = TYPE_BUY) to false,
            Data("TODO", "05.01.2022", type = TYPE_TODO) to false,
            Data("TODO", "05.01.2022", type = TYPE_TODO) to false,
            Data("BUY", "", type = TYPE_BUY) to false
        )
// добавляем заголовок
        data.add(0, Data("Дела и покупки", type = TYPE_HEADER) to false)

        val adapter = RecyclerFragmentAdapter(data,
            object : MyCallback {
                override fun onClick(position: Int) {
                    Toast.makeText(
                        context,
                        "РАБОТАЕТ ${data[position].first.label} ${data[position].first.description}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        binding.recyclerView.adapter = adapter

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RecycleFragment()
    }


}