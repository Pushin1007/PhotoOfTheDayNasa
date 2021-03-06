package com.pd.photo_of_the_day_nasa.view.settings

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.transition.*

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.Fade
import androidx.transition.TransitionSet
import com.pd.photo_of_the_day_nasa.*
import com.pd.photo_of_the_day_nasa.R
import com.pd.photo_of_the_day_nasa.databinding.FragmentSettingsBinding
import com.pd.photo_of_the_day_nasa.view.MainActivity
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener


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
    private var isChipGroupVisible = false
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
        binding.changeThemeButton.setOnClickListener {// при нажатии на кнопку показываем чипы тем
            isChipGroupVisible = !isChipGroupVisible
            val transition = TransitionSet() // несколько анимаций
            val slide = Slide(Gravity.END)
            slide.duration =
                ANIMATION_TIME_LONG// длительность анимации, можно задавать как вместе так и по отдетности
            val changeBounds = ChangeBounds()
            slide.interpolator = AnticipateOvershootInterpolator(2f)// делаем как на пружинке
            changeBounds.duration = ANIMATION_TIME_SHORT
            transition.addTransition(slide)//вид анимации слайд
            transition.addTransition(changeBounds)//вид анимации изсенение размеров
            //val transition = AutoTransition()// сама определяет

            TransitionManager.beginDelayedTransition(binding.settings, transition)
            binding.chipThemeGroup.visibility = if (isChipGroupVisible) View.VISIBLE else View.GONE
            //binding.changeThemeButton.text = "Меняй" можно сменить текст на кнопке

            //Анимируем другим способом c помощью ObjectAnimator
            ObjectAnimator.ofFloat(binding.changeThemeButton, "rotation", 0f, 360f)
                .setDuration(ANIMATION_TIME_SHORT).start() //вращаем
//            ObjectAnimator.ofFloat(binding.changeThemeButton, "alpha", 0f).start()// делаем прозрачным
            ObjectAnimator.ofFloat(binding.changeThemeButton, "translationY", -300f)
                .setDuration(ANIMATION_TIME_SHORT).start()// смещаем

            // еще один способ анимации
            binding.changeThemeButton.animate()
                .alpha(0f)
                .setDuration(ANIMATION_TIME_SHORT)
                .setListener(object : AnimatorListenerAdapter() { //улавливаем конец анимации
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.changeThemeButton.isClickable =
                            false // делаем кнопку не кликабельной
                        Toast.makeText(context, "Теперь можно выбрать тему", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }


        binding.buttonInfo.setOnClickListener { // изменение  Constraint в коде без применения MotionLayout и  двух активити

            val constraintSet = ConstraintSet()

            constraintSet.clone(context, R.layout.fragment_settings) //клонируем макет
            constraintSet.connect(
                R.id.header_text,
                ConstraintSet.START,
                R.id.settings,
                ConstraintSet.START
            )// програмно меняем в нем  Constraint

            val transition = ChangeBounds()
            transition.duration = ANIMATION_TIME_SHORT
            transition.interpolator = AnticipateOvershootInterpolator(5f) //пружинка

            TransitionManager.beginDelayedTransition(binding.settings, transition)
            constraintSet.applyTo(binding.settings) //применяем наш сет

        }

// подсказка (Гайд) на кнопке
        val builder = GuideView.Builder(requireContext())
            .setTitle(getString(R.string.fitch))
            .setContentText(getString(R.string.fitch_description))
            .setGravity(smartdevelop.ir.eram.showcaseviewlib.config.Gravity.center)
            .setDismissType(DismissType.selfView) // закрытие подсказки только на вьюшку
            .setTargetView(binding.changeThemeButton)
            .setDismissType(DismissType.anywhere)// закрытие подсказки  при тапе в любом месте
            .setGuideListener(object : GuideListener {
                override fun onDismiss(view: View?) {

                }
            })
        builder.build().show()

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
