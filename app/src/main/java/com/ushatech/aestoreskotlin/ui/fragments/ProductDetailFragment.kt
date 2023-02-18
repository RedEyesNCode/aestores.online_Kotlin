package com.ushatech.aestoreskotlin.ui.fragments

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.databinding.FragmentProductDetailBinding
import com.ushatech.aestoreskotlin.ui.adapter.ImageViewPagerAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductDetailFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding:FragmentProductDetailBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        initClicks()
        binding.viewPagerProductImage.adapter = ImageViewPagerAdapter(fragmentContext)
        binding.viewPagerProductImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val currentPageIndex = 0
        binding.viewPagerProductImage.currentItem = currentPageIndex
        binding.viewPagerProductImage.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvNumberImages.text = "${position + 1} / 2"

                }
            }
        )
        binding.tvMaxPrice.setPaintFlags(binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initClicks() {
        binding.ivCart.setOnClickListener {
            FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,CartFragment(),CartFragment::class.java.canonicalName,true)


        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}