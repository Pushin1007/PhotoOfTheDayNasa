package com.pd.photo_of_the_day_nasa.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pd.photo_of_the_day_nasa.NUMBER_OF_THEME
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
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
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// не пойму как поменять тему
        val bundle = Bundle()
        binding.chipThemeGroup.setOnCheckedChangeListener { _, Id ->
            when (Id) {
                R.id.chipThemeGray -> {
                    bundle.putInt(NUMBER_OF_THEME, 1)
                }
                R.id.chipThemePurple -> {
                    bundle.putInt(NUMBER_OF_THEME, 2)
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}
