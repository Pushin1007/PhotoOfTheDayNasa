package com.pd.photo_of_the_day_nasa.view.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.FragmentCoordinatorBinding
import com.pd.photo_of_the_day_nasa.view.picture.PictureOfTheDayFragment

class CoordinatorFragment : Fragment() {


    private var _binding: FragmentCoordinatorBinding? = null
    val binding: FragmentCoordinatorBinding
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
        _binding = FragmentCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedBehavior = NestedBehavior() // подключение  Behavior через код
        // Behavior кнопки подключен через  Layout

        (binding.nested.layoutParams as CoordinatorLayout.LayoutParams).behavior = nestedBehavior
        binding.myButton.setOnClickListener() {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(// делаем анимацию между фрегментами
                    R.animator.enter_from_right,
                    R.animator.exit_to_left,
                    R.animator.enter_from_left,
                    R.animator.exit_to_right
                ).replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }
    }// получаем параметры Nested, извлекаем параметры,явно приводим и вызываем behavior

    companion object {
        @JvmStatic
        fun newInstance() =
            CoordinatorFragment()
    }


}