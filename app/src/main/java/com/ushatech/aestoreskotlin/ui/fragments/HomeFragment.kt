package com.ushatech.aestoreskotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.databinding.FragmentHomeBinding
import com.ushatech.aestoreskotlin.ui.adapter.FeaturedCategoryAdapter
import com.ushatech.aestoreskotlin.ui.adapter.ImageViewPagerAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class HomeFragment : BaseFragment(), FeaturedCategoryAdapter.onClickCategory {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHomeBinding

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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initClicks()
        setupRecyler()
        return binding.root

    }

    override fun onCategoryClick(position: Int) {
        FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,CategoryProductFragment(),CategoryProductFragment::class.java.canonicalName,true)
    }

    private fun setupRecyler() {
        binding.recvFeaturedCategories.adapter= FeaturedCategoryAdapter(fragmentContext,this)
        binding.recvFeaturedCategories.layoutManager = GridLayoutManager(fragmentContext,2,GridLayoutManager.VERTICAL,false)

        binding.viewPagerTrending.adapter = ImageViewPagerAdapter(fragmentContext)
        binding.viewPagerTrending.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPagerRecentItems.adapter = ImageViewPagerAdapter(fragmentContext)
        binding.viewPagerRecentItems.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        binding.viewPagerArrival.adapter = ImageViewPagerAdapter(fragmentContext)
        binding.viewPagerArrival.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val currentPageIndex = 0
        binding.viewPagerArrival.currentItem = currentPageIndex
        binding.viewPagerTrending.currentItem = currentPageIndex
        binding.viewPagerRecentItems.currentItem = currentPageIndex
        binding.viewPagerTrending.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvNumberImages.text = "${position + 1} / 2"

                }
            }
        )
        binding.viewPagerArrival.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvNumberPagesArrival.text = "${position + 1} / 2"

                }
            }
        )
        binding.viewPagerRecentItems.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvNumberPagesArrival.text = "${position + 1} / 2"

                }
            }
        )

    }
    override fun onDestroy() {
        super.onDestroy()

        // unregistering the onPageChangedCallback
        binding.viewPagerTrending.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }
    private fun initClicks() {


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}