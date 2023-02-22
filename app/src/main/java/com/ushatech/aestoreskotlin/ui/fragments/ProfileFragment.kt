package com.ushatech.aestoreskotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.intuit.sdp.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.databinding.FragmentProfileBinding
import com.ushatech.aestoreskotlin.ui.adapter.ViewPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentProfileBinding




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

        binding = FragmentProfileBinding.inflate(layoutInflater)

        initClicks()
        setupTabLayout()
        return binding.root
    }

    private fun setupTabLayout() {
        val dashboardTab = binding.tabLayout.newTab()
        dashboardTab.text = "Dashboard"
        dashboardTab.select()

        binding.tabLayout.addTab(dashboardTab)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Edit Profile"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("User referrals"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Learning program"))
        val viewPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.tabViewpager.setAdapter(viewPagerAdapter)
        TabLayoutMediator(binding.tabLayout, binding.tabViewpager) { tab, position ->
            if(position==0){
                tab.setText("Dashboard")
            }else if(position==1){
                tab.setText("Edit Profile")
            }else if(position==2){
                tab.setText("User referrals")
            }else if(position==3){
                tab.setText("Learning program")
            }

        }.attach()
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(
                resources.getDimensionPixelOffset(R.dimen._5sdp),
                resources.getDimensionPixelOffset(
                    R.dimen._8sdp
                ),
                resources.getDimensionPixelOffset(R.dimen._5sdp),
                resources.getDimensionPixelOffset(
                    R.dimen._8sdp
                )
            )
            tab.requestLayout()
        }
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}