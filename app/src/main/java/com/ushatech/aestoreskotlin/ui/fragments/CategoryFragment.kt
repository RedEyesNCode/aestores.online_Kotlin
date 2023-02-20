package com.ushatech.aestoreskotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.FragmentCategoryBinding
import com.ushatech.aestoreskotlin.presentation.DashboardViewModel
import com.ushatech.aestoreskotlin.ui.adapter.DrawerAdapter
import com.ushatech.aestoreskotlin.ui.adapter.FeaturedCategoryAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : BaseFragment() , FeaturedCategoryAdapter.onClickCategory{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentCategoryBinding

    private lateinit var viewModel:DashboardViewModel
    override fun onCategoryClick(position: Int) {
        FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,CategoryProductFragment(),CategoryProductFragment::class.java.canonicalName,true)

    }

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
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        setupViewModel()
        attachObservers()
        //Initial Api call
        showLoader()
        viewModel.getHomeScreenResponse()
        return binding.root
    }

    private fun attachObservers() {
        viewModel.isFailed.observe((viewLifecycleOwner)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        viewModel.isSuccess.observe((viewLifecycleOwner)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }

        viewModel.homeScreenResponse.observe((viewLifecycleOwner)){
            hideLoader()
            showLog("${it.data?.categories?.size}")
            setupFeaturedCategories(it.data?.categories)
        }
    }
    private fun setupFeaturedCategories(categories: ArrayList<HomeScreenResponse.Categories>?) {
        binding.recvFeaturedCategories.adapter= FeaturedCategoryAdapter(fragmentContext,categories!!,this)
        binding.recvFeaturedCategories.layoutManager = GridLayoutManager(fragmentContext,2,
            GridLayoutManager.VERTICAL,false)


    }
    private fun setupViewModel() {
        viewModel = DashboardViewModel()
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding.viewmodel = viewModel

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}