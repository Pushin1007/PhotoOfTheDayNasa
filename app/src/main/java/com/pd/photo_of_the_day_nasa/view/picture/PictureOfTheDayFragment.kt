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
import com.pd.photo_of_the_day_nasa.view.api_nasa.ApiActivity
import com.pd.photo_of_the_day_nasa.view.api_nasa.ApiBottomActivity
import com.pd.photo_of_the_day_nasa.view.settings.SettingsFragment
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayState
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

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
        binding.chipsGroup.check(R.id.chipToday) // выбранная кнопка по умолчанию
        binding.chipsGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chipToday -> {
                    viewModel.sendServerRequest()
                }
                R.id.chipYesterday -> {
                    viewModel.sendServerRequest(takeDate(-1))
                }
                R.id.chipDayBeforeYesterday -> {
                    viewModel.sendServerRequest(takeDate(-2))
                }
                else -> viewModel.sendServerRequest()
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

    private fun takeDate(count: Int): String { // преобразовываем дату в нужном формате
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())// приводим к нужному формату
        format1.timeZone = TimeZone.getTimeZone("EST") // меняем временную зону
        return format1.format(currentDate.time)
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
                setData(state)
            }
        }
    }


    private fun setData(data: PictureOfTheDayState.Success) {// определяем что пришло, картинка или видео
//        val url = data.pictureOfTheDayResponseData.url // проверка по медиатайп
        val url = data.pictureOfTheDayResponseData.hdurl //  // если hdurl пустое то пришло видео
        val header = data.pictureOfTheDayResponseData.title
        val description = data.pictureOfTheDayResponseData.explanation
//        if (url.isNullOrEmpty()) { // если hdurl пустое то пришло видео
        if (data.pictureOfTheDayResponseData.mediaType == "video") { // проверка по медиа тайп
            val videoUrl = data.pictureOfTheDayResponseData.url
            videoUrl?.let { showAVideoUrl(it) }
        } else { // если пришла картинка
            binding.imageView.visibility = View.VISIBLE
            binding.youtubePlayerView.visibility = View.GONE
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

    private fun showAVideoUrl(videoUrl: String) =
        with(binding) {//показываем видео, скрываем картинку
            binding.imageView.visibility = View.GONE
/*
//Способ открытия видео через интент в приложении ютуба
            videoOfTheDay.visibility = View.VISIBLE
            videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                    "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
            videoOfTheDay.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(videoUrl)
                }
                startActivity(i)
            }
*/

//способ открытия через встроенный ютуб
            youtubePlayerView.visibility = View.VISIBLE
            lifecycle.addObserver(binding.youtubePlayerView)
            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {

                    extractId(videoUrl)?.let {
                        youTubePlayer.loadVideo(
                            it,
                            0f
                        )
                    } // проверка на ноль
//                                        youTubePlayer.loadVideo("2SnbMTQwDKM", 0f)//для проверки id
                }
            })
        }

    fun extractId(ytUrl: String): String? { // выдергиваем id видео из  полного url

        var vId: String? = null
        val pattern: Pattern = Pattern.compile(
            "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
            Pattern.CASE_INSENSITIVE
        )
        val matcher: Matcher = pattern.matcher(ytUrl)
        if (matcher.matches()) {
            vId = matcher.group(1)
        }
        return vId
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
            R.id.photo_library -> startActivity(
                Intent(
                    requireContext(),
                    ApiActivity::class.java
                )
            )
            R.id.photo_libraryBND -> startActivity(
                Intent(
                    requireContext(),
                    ApiBottomActivity::class.java
                )
            )

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