package com.pd.photo_of_the_day_nasa.view.chips
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
//            group.findViewById<Chip>(checkedId)?.let{
//                Toast.makeText(context,"choose ${it.text}",Toast.LENGTH_SHORT).show()
//            }
//
//        }

//        binding.chipForDelete.setOnCloseIconClickListener {
//            //binding.chipForDelete.visibility = View.GONE
//            binding.chipForDelete.isChecked = false
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}