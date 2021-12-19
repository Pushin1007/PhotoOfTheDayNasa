package com.pd.photo_of_the_day_nasa.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.view.*
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar

import androidx.lifecycle.Observer
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.FragmentMainBinding
import com.pd.photo_of_the_day_nasa.view.MainActivity
import com.pd.photo_of_the_day_nasa.view.settings.SettingsFragment
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayState
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendServerRequest()

        binding.chipsGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipToday -> {
// вот здесь не пойму как обработать....
//                    chipsDayGroup.chipsGroup.check(R.id.chipToday)
//                    viewModel.getPODFromServer(getDay(TODAY))
                }
                R.id.chipYesterday -> {
//                    chipsDayGroup.chipsGroup.check(R.id.chipYesterday)
//                    viewModel.getPODFromServer(getDay(YESTERDAY))
                }
                R.id.chipDayBeforeYesterday -> {
//                    chipsDayGroup.chipsGroup.check(R.id.chipDayBeforeYesterday)
//                    viewModel.getPODFromServer(getDay(BEFORE_YESTERDAY))
                }
//                else -> viewModel.getPODFromServer(getDay(TODAY))
            }
        }


        binding.inputLayout.setEndIconOnClickListener { //при нажатии на кнопку wiki  ищем введенный текст
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                // достаем текст из вьюхи, преобразовываем в текст запрашиваем в вики
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        setBottomAppBar()

    }

    private fun renderData(state: PictureOfTheDayState) {
        when (state) {
            is PictureOfTheDayState.Error -> {
                binding.imageView.load(R.drawable.ic_load_error_vector)
                Toast.makeText(context, "PictureOfTheDayState.Error ", Toast.LENGTH_LONG).show()
            }
            is PictureOfTheDayState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is PictureOfTheDayState.Success -> {
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                val header = pictureOfTheDayResponseData.title
                val description = pictureOfTheDayResponseData.explanation
                binding.imageView.load(url)
                {
                    lifecycle(this@PictureOfTheDayFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
                binding.includeBottomSheet.bottomSheetDescriptionHeader.text = header
                binding.includeBottomSheet.bottomSheetDescription.text = description
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): PictureOfTheDayFragment {
            return PictureOfTheDayFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { // создаем меню
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()


            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SettingsFragment.newInstance()

                ).addToBackStack(null).commit()
            android.R.id.home -> BottomNavigationDrawerFragment().show(
                requireActivity().supportFragmentManager,
                ""
            )


            android.R.id.home -> BottomNavigationDrawerFragment().show( //подключение бургера
                requireActivity().supportFragmentManager, ""
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private var isMain = true
    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener { // смена положенияцентральной кнопки
            if (isMain) { // если на главном экране.(виртаульно!) экран не меняется
                isMain = false
                binding.bottomAppBar.navigationIcon = null // скрываем бургер
                binding.bottomAppBar.fabAlignmentMode =
                    BottomAppBar.FAB_ALIGNMENT_MODE_END //меняем положение кнопки
                binding.fab.setImageDrawable( // меняем иконку "+" кнопки на стрелку
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen) // меняется меню
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )  // возвращаем гамбургер
                binding.bottomAppBar.fabAlignmentMode =
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER //меняем положение кнопки
                binding.fab.setImageDrawable( // меняем иконку кнопки стрелку на "+"
                    ContextCompat.getDrawable(
                        context,

                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }


    }


}