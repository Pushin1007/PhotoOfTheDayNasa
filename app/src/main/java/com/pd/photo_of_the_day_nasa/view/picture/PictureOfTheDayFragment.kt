package com.pd.photo_of_the_day_nasa.view.picture

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.util.Log

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar

import androidx.lifecycle.Observer
import com.pd.photo_of_the_day_nasa.ANIMATION_TIME_LONG
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.FragmentMainStartBinding

import com.pd.photo_of_the_day_nasa.view.MainActivity
import com.pd.photo_of_the_day_nasa.view.api_nasa.ApiActivity
import com.pd.photo_of_the_day_nasa.view.api_nasa.ApiBottomActivity
import com.pd.photo_of_the_day_nasa.view.settings.SettingsFragment
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayState
import com.pd.photo_of_the_day_nasa.viewmodel.PictureOfTheDayViewModel
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainStartBinding? = null
    val binding: FragmentMainStartBinding
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

    private var isExpand = false

    private var isFabHide = false

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
        //гайд по чипам фоток
        val builder = GuideView.Builder(requireContext())
            .setTitle("Фото дней минувших")
            .setContentText("Можно посмотреть фото текущего дня,  а также выбрать вчерашнее фото и фото двухдневной давности.")
            .setGravity(Gravity.center)
            .setTargetView(binding.chipsGroup)
            .setDismissType(DismissType.anywhere)
            .setGuideListener(object : GuideListener {
                override fun onDismiss(view: View?) {

                }
            })
        builder.build().show()

        binding.inputLayout.setEndIconOnClickListener { //при нажатии на кнопку wiki  ищем введенный текст
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                // достаем текст из вьюхи, преобразовываем в текст запрашиваем в вики
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        binding.imageView.setOnClickListener { // Увеличиваем картинку по нажатию
            isExpand = !isExpand
            val params =
                binding.imageView.layoutParams as ViewGroup.LayoutParams //Возвращаем параметры контейнера

            val transition = ChangeImageTransform() //специальная трансформация для картинок
            transition.duration = ANIMATION_TIME_LONG
            TransitionManager.beginDelayedTransition(binding.main, transition) // вызов анимации

            if (isExpand) {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                params.height =
                    ViewGroup.LayoutParams.MATCH_PARENT // меняем параметры контейнера- работаем наружу
            } else {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            binding.imageView.layoutParams = params
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


    private fun setData(data: PictureOfTheDayState.Success) =
        with(binding) {// определяем что пришло, картинка или видео

            val url = data.pictureOfTheDayResponseData.url // проверка по медиатайп
//        val url = data.pictureOfTheDayResponseData.hdurl //  // если hdurl пустое то пришло видео

            //Header
            includeBottomSheet.bottomSheetDescriptionHeader.typeface =
                Typeface.createFromAsset(
                    requireContext().assets,
                    "script_regular.ttf"
                )// применяем шрифт в коде

            data.pictureOfTheDayResponseData.title?.let { //header

                val spannableHeader = SpannableStringBuilder(it)
                spannableHeader.setSpan(//подчеркивание
                    UnderlineSpan(),
                    0,
                    it.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )

                val blurMaskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.SOLID) // размытие
                spannableHeader.setSpan(
                    MaskFilterSpan(blurMaskFilter),
                    0,
                    it.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
                spannableHeader.setSpan(// выделение абзаца. Выглядит так себе. Для тренировки
                    QuoteSpan(Color.RED), 0,
                    it.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
                for (i in spannableHeader.indices) {
                    if (spannableHeader[i] == 'o') {
                        spannableHeader.setSpan(
                            ImageSpan(
                                requireContext(),
                                R.drawable.ic_earth
                            ), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                }
                includeBottomSheet.bottomSheetDescriptionHeader.text = spannableHeader
            }


//Description
            data.pictureOfTheDayResponseData.explanation?.let {
                initSpans(it)

            }


            //if (url.isNullOrEmpty()) { // если hdurl пустое то пришло видео
            if (data.pictureOfTheDayResponseData.mediaType == "video") { // проверка по медиа тайп
                val videoUrl = data.pictureOfTheDayResponseData.url
//                youtubePlayerView.visibility = View.VISIBLE
                imageView.visibility = View.GONE

                videoUrl?.let { showAVideoUrl(it) }

            } else { // если пришла картинка
                imageView.visibility = View.VISIBLE
//                youtubePlayerView.visibility = View.GONE
                imageView.load(url)

                {
                    lifecycle(this@PictureOfTheDayFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }

            }
        }

    private fun initSpans(text: String) {
        val requestCallback = FontRequest(
            "com.google.android.gms.fonts", "com.google.android.gms",
            "Tienne", R.array.com_google_android_gms_fonts_certs
        )
        val callback =
            object : FontsContractCompat.FontRequestCallback() { //подгружаем шрифты через callback
                override fun onTypefaceRetrieved(typeface: Typeface?) {
                    typeface?.let {
                        val spannableDescriptionStart = SpannableStringBuilder(text)
                        binding.includeBottomSheet.bottomSheetDescription.setText(
                            spannableDescriptionStart,
                            TextView.BufferType.EDITABLE
                        )
                        val spannableDescription =
                            binding.includeBottomSheet.bottomSheetDescription.text as SpannableStringBuilder
                        spannableDescription.setSpan(//делаем первую букву красной Как в книжке
                            ForegroundColorSpan(Color.RED),//цвет
                            0,
                            1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            spannableDescription.setSpan(
                                TypefaceSpan(it),
                                0, spannableDescription.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                        spannableDescription.setSpan(
                            AbsoluteSizeSpan(16, true),
                            0,
                            spannableDescription.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        spannableDescription.setSpan(
                            StyleSpan(Typeface.ITALIC),
                            0,
                            spannableDescription.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )


                        spannableDescription.setSpan( //делаем первую букву большой
                            RelativeSizeSpan(3.0f), // коэффициент умножения
                            0,
                            1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )


                        spannableDescription.insert(
                            spannableDescription.length,
                            " :)"
                        ) //вставляем текст
                    }

                }

            }
        val handler = Handler(Looper.getMainLooper())
        FontsContractCompat.requestFont(requireContext(), requestCallback, callback, handler)
    }

    private fun showAVideoUrl(videoUrl: String) =
        with(binding) {//показываем видео, скрываем картинку

//Способ открытия видео через интент в приложении ютуба
            videoOfTheDay.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                    "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
            videoOfTheDay.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(videoUrl)
                }
                startActivity(i)
            }

/*
//способ открытия через встроенный ютуб
            lifecycle.addObserver(youtubePlayerView)
            youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    extractId(videoUrl)?.let {
                        youTubePlayer.loadVideo(
                            it,
                            0f
                        )
                    } // проверка на ноль
     //                                   youTubePlayer.loadVideo("2SnbMTQwDKM", 0f)//для проверки id
                }
            })

 */
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
        _binding = FragmentMainStartBinding.inflate(inflater, container, false)
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