package com.pd.photo_of_the_day_nasa.view.picture

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pd.photo_of_the_day_nasa.databinding.FragmentMainBinding

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

//    private val viewModel: PictureOfTheDayViewModel by lazy {
//        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.getData().observe(viewLifecycleOwner, Observer {
//            renderData(it)
//        })
//        viewModel.sendServerRequest()
//
//        binding.inputLayout.setEndIconOnClickListener {
//            startActivity(Intent(Intent.ACTION_VIEW).apply {
//                data =
//                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
//            })
//        }
//
//        val behavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)
//        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//
//
//        behavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    /* BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
//                     BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
//                     BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
//                     BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
//                     BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
//                     BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")*/
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                Log.d("mylogs", "$slideOffset slideOffset")
//
//                //TODO("not implemented")
//            }
//        })
//
//        setBottomAppBar()
//
//    }

//    private fun renderData(state: PictureOfTheDayState) {
//        when (state) {
//            is PictureOfTheDayState.Error -> {//TODO(ДЗ)
//            }
//            is PictureOfTheDayState.Loading -> {
//                binding.imageView.load(R.drawable.ic_no_photo_vector)
//            }
//            is PictureOfTheDayState.Success -> {
//                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
//                val url = pictureOfTheDayResponseData.url
//                binding.imageView.load(url) {
//                    lifecycle(this@PictureOfTheDayFragment)
//                    error(R.drawable.ic_load_error_vector)
//                    placeholder(R.drawable.ic_no_photo_vector)
//                }
//            }
//        }
//    }

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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu_bottom_bar, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
//            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
//                .replace(
//                    R.id.container,
//                    ChipsFragment.newInstance()
//                ).commit()
//            android.R.id.home -> BottomNavigationDrawerFragment().show(
//                requireActivity().supportFragmentManager,
//                ""
//            )
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private var isMain = true
//    private fun setBottomAppBar() {
//        val context = activity as MainActivity
//        context.setSupportActionBar(binding.bottomAppBar)
//        setHasOptionsMenu(true)
//
//
//        binding.fab.setOnClickListener {
//            if (isMain) {
//                isMain = false
//                binding.bottomAppBar.navigationIcon = null
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.ic_back_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
//            } else {
//                isMain = true
//                binding.bottomAppBar.navigationIcon =
//                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.ic_plus_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
//            }
//        }
//
//        requireActivity()
//            .onBackPressedDispatcher
//            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    Log.d(ContentValues.TAG, "Fragment back pressed invoked")
//                    if (isMain) {
//                        System.exit(0)
//                    } else {
//                        isMain = true
//                        binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(
//                            context,
//                            R.drawable.ic_hamburger_menu_bottom_bar
//                        )
//                        binding.bottomAppBar.fabAlignmentMode =
//                            BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                        binding.fab.setImageDrawable(
//                            ContextCompat.getDrawable(
//                                context,
//                                R.drawable.ic_plus_fab
//                            )
//                        )
//                        binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
//                    }
//                }
//            }
//            )
//
//
//    }


}