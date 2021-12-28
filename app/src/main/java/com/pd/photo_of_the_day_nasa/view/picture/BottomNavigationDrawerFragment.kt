package com.pd.photo_of_the_day_nasa.view.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.BottomNavigationLayoutBinding
import com.pd.photo_of_the_day_nasa.view.coordinator.CoordinatorFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() {
            return _binding!!
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
// обрабатываем нажатия
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CoordinatorFragment.newInstance()).addToBackStack("").commit()
                }
                R.id.navigation_two -> {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_three -> {
                    Toast.makeText(context, "3", Toast.LENGTH_SHORT).show()
                }
            }
            dismiss()// скрытие меню
            true
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


}