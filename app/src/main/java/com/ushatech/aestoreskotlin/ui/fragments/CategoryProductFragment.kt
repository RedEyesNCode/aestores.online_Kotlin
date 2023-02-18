package com.ushatech.aestoreskotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.databinding.FragmentCategoryBinding
import com.ushatech.aestoreskotlin.databinding.FragmentCategoryProductBinding
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

    override fun onProductClick(position: Int) {
        FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,ProductDetailFragment(),ProductDetailFragment::class.java.canonicalName,true)

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
        setupRecyclerView()


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recvCategoryProduct.adapter = CategoryProductAdapter(fragmentContext,this)

        binding.recvCategoryProduct.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.VERTICAL,false)


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