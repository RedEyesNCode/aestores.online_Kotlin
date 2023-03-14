package com.ushatech.aestoreskotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.data.SearchProductResponse
import com.ushatech.aestoreskotlin.databinding.FragmentCategoryProductBinding
import com.ushatech.aestoreskotlin.presentation.CategoryProductViewModel
import com.ushatech.aestoreskotlin.session.Constant
import com.ushatech.aestoreskotlin.ui.adapter.CategoryProductAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CategoryProductFragment : BaseFragment(),CategoryProductAdapter.onClickEvent {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding:FragmentCategoryProductBinding
    private lateinit var viewModel:CategoryProductViewModel

    override fun onProductClick(position: Int,productId:String) {
        FragmentUtils().addFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,
            ProductDetailFragment.newInstance(productId,""),
            ProductDetailFragment::class.java.canonicalName,true)

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
        // Inflate the layout for this fragment
        binding = FragmentCategoryProductBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCall()

        return binding.root
    }

    private fun initialApiCall() {
        showLoader()
        if(param2?.toInt()==Constant.CATEGORY_KEY){
            viewModel.searchProduct(null,param1?.toInt(),null,null)

        }else{
            viewModel.searchProduct(null,null,param1?.toInt(),null)

        }


    }

    private fun setupViewModel() {
        viewModel = CategoryProductViewModel()
        viewModel = ViewModelProvider(this).get(CategoryProductViewModel::class.java)
        binding.viewmodel = viewModel
    }
    private fun attachObservers(){
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
        viewModel.searchProductResponse.observe(viewLifecycleOwner){
            hideLoader()
            if(it!=null){
                if(it.status==1){
                    setupRecyclerView(it.data)

                }else{
                    binding.recvCategoryProduct.visibility = View.GONE
                    binding.tvNoCartItem.visibility = View.VISIBLE
                    showToast(it.message.toString())

                }
            }else{
                showToast(Constant.OOPS_SW)
            }



        }





    }

    private fun initClicks() {




    }

    private fun setupRecyclerView(data: SearchProductResponse.Data?) {
        binding.recvCategoryProduct.adapter = CategoryProductAdapter(fragmentContext, data!!,this)

        binding.recvCategoryProduct.layoutManager = GridLayoutManager(fragmentContext,2)


    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}