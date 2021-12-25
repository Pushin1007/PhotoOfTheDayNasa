package com.pd.photo_of_the_day_nasa.view.api_nasa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.FragmentEarthBinding
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayViewModel
import coil.load
import com.pd.photo_of_the_day_nasa.BuildConfig
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayState
import androidx.lifecycle.Observer

class EarthFragment : Fragment() {
    private var _binding: FragmentEarthBinding? = null
    val binding: FragmentEarthBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEarthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getLiveData().observe(viewLifecycleOwner,{ render(it) })
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            render(it)
        })
        viewModel.getEpic()

    }

    private fun render(appState: PictureOfTheDayState) {
        when (appState) {
            is PictureOfTheDayState.Error -> {
                binding.imageView.load(R.drawable.ic_load_error_vector)
                Toast.makeText(context, "PictureOfTheDayState.Error ", Toast.LENGTH_LONG).show()
            }
            is PictureOfTheDayState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }

            is PictureOfTheDayState.SuccessEarthEpic -> {

                val strDate = appState.pictureOfTheDayResponseData.last().date.split(" ").first()
                val image = appState.pictureOfTheDayResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.imageView.load(url)
            }


        }
    }
}

