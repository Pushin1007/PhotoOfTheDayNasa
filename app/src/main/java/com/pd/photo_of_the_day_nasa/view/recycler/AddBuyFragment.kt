package com.pd.photo_of_the_day_nasa.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.pd.photo_of_the_day_nasa.*

import com.pd.photo_of_the_day_nasa.databinding.FragmentAddBuyBinding


class AddBuyFragment : Fragment() {

    private var _binding: FragmentAddBuyBinding? = null
    val binding: FragmentAddBuyBinding
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
        _binding = FragmentAddBuyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addLabel = binding.addBuyLabel
        val addDescription = binding.addBuyDescription
        binding.addBtnSave.setOnClickListener {
            val data =
                Data(addLabel.text.toString(), addDescription.text.toString(), type = TYPE_BUY)

            val bundle = Bundle()
            bundle.putParcelable(ARG_BUY_ADD, data)
            parentFragmentManager
                .setFragmentResult(KEY_BUY_RESULT_ADD, bundle)
            parentFragmentManager.popBackStack()

        }
    }
}
