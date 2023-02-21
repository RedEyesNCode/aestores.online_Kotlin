package com.ushatech.aestoreskotlin.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.permissionx.guolindev.PermissionX
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.FragmentHomeBinding
import com.ushatech.aestoreskotlin.presentation.DashboardViewModel
import com.ushatech.aestoreskotlin.ui.adapter.FeaturedCategoryAdapter
import com.ushatech.aestoreskotlin.ui.adapter.ImageViewPagerArrivalAdapter
import com.ushatech.aestoreskotlin.ui.adapter.ImageViewPagerTrendingAdapter
import com.ushatech.aestoreskotlin.ui.adapter.ViewPagerBanner
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
    private lateinit var dashBoardViewModel:DashboardViewModel





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
        setupViewModel()
        attachObservers()
        initApiCall()
        return binding.root

    }

    private fun initApiCall() {
        dashBoardViewModel.getHomeScreenResponse()
    }

    private fun attachObservers() {


        dashBoardViewModel.isFailed.observe((viewLifecycleOwner)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        dashBoardViewModel.isSuccess.observe((viewLifecycleOwner)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }

        dashBoardViewModel.homeScreenResponse.observe((viewLifecycleOwner), {
            hideLoader()
            showLog("${it.data?.categories?.size}")
            setupFeaturedCategories(it.data?.categories)
            setupTrendingViewPager(it.data?.trending)
            setupArrivalViewPager(it.data?.arrival)
            setupBannerViewPager(it.data?.slides)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                checkForNotificationPermission()
//            }
        })


    }

    private fun setupBannerViewPager(slides: java.util.ArrayList<HomeScreenResponse.Slides>?) {
        binding.viewPagerBanner.adapter = ViewPagerBanner(fragmentContext,slides!!)
        binding.viewPagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val currentPageIndex = 0
        binding.viewPagerBanner.currentItem = currentPageIndex

        binding.viewPagerBanner.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvBannerNumber.text = "${position + 1} / ${slides.size}"

                }
            }
        )

    }

    private fun setupArrivalViewPager(arrival: java.util.ArrayList<HomeScreenResponse.Arrival>?) {
        binding.viewPagerArrival.adapter = ImageViewPagerArrivalAdapter(fragmentContext, arrival!!)
        binding.viewPagerArrival.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val currentPageIndex = 0
        binding.viewPagerArrival.currentItem = currentPageIndex

        binding.viewPagerArrival.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvNumberPagesArrival.text = "${position + 1} / ${arrival.size}"

                }
            }
        )
    }

    private fun setupTrendingViewPager(trending: java.util.ArrayList<HomeScreenResponse.Trending>?) {
        binding.viewPagerTrending.adapter = ImageViewPagerTrendingAdapter(fragmentContext, trending!!)
        binding.viewPagerTrending.orientation = ViewPager2.ORIENTATION_HORIZONTAL
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
        binding.viewPagerTrending.currentItem = 0
    }

    private fun setupFeaturedCategories(categories: ArrayList<HomeScreenResponse.Categories>?) {
        binding.recvFeaturedCategories.adapter= FeaturedCategoryAdapter(fragmentContext,categories!!,this)
        binding.recvFeaturedCategories.layoutManager = GridLayoutManager(fragmentContext,2,GridLayoutManager.VERTICAL,false)


    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkForNotificationPermission() {
        PermissionX.init(requireActivity())
            .permissions(Manifest.permission.POST_NOTIFICATIONS)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    hideLoader()

                    showLog("Granted for notifications")
                } else {
                    hideLoader()
                    showLog("Not granted for notifications")
                }
            }





    }
    private fun setupViewModel() {
        dashBoardViewModel = DashboardViewModel()
        dashBoardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding.viewmodel = dashBoardViewModel


    }

    override fun onCategoryClick(position: Int) {
        FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,CategoryProductFragment(),CategoryProductFragment::class.java.canonicalName,true)
    }

    private fun setupRecyler() {

//        binding.viewPagerTrending.adapter = ImageViewPagerAdapter(fragmentContext)
//        binding.viewPagerTrending.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//        binding.viewPagerRecentItems.adapter = ImageViewPagerAdapter(fragmentContext)
//        binding.viewPagerRecentItems.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//
//        binding.viewPagerArrival.adapter = ImageViewPagerAdapter(fragmentContext)
//        binding.viewPagerArrival.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val currentPageIndex = 0
        binding.viewPagerArrival.currentItem = currentPageIndex
        binding.viewPagerTrending.currentItem = currentPageIndex
        binding.viewPagerRecentItems.currentItem = currentPageIndex


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