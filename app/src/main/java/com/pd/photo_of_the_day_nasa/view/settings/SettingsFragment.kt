package com.pd.photo_of_the_day_nasa.view.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.ThemeOne
import com.pd.photo_of_the_day_nasa.ThemeSecond
import com.pd.photo_of_the_day_nasa.databinding.FragmentSettingsBinding
import com.pd.photo_of_the_day_nasa.view.MainActivity


class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var parentActivity: MainActivity // Получаем родительскую активити
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity =
            requireActivity() as MainActivity //  со встроенной проверкой актвити на null)
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater) // здесь inflater родительской активити
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipThemeGray.setOnClickListener(this)
        binding.chipThemePurple.setOnClickListener(this)

        when (parentActivity.getCurrentTheme()) {
            1 -> binding.chipThemeGroup.check(R.id.chipThemeGray)
            2 -> binding.chipThemeGroup.check(R.id.chipThemePurple)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.chipThemeGray -> {
                parentActivity.setCurrentTheme(ThemeOne)
                parentActivity.recreate() // применяем для всей активити и для всех дочерних фрагментов
            }
            R.id.chipThemePurple -> {
                parentActivity.setCurrentTheme(ThemeSecond)
                parentActivity.recreate() // применяем для всей активити и для всех дочерних фрагментов
            }
        }

    }

    companion object {
        fun newInstance() = SettingsFragment()
    }


}
