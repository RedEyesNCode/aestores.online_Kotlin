package com.ushatech.aestoreskotlin.ui.fragments

import android.app.Dialog
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.base.BaseFragment
import com.ushatech.aestoreskotlin.databinding.FragmentProductDetailBinding
import com.ushatech.aestoreskotlin.databinding.ImageDialogBinding
import com.ushatech.aestoreskotlin.ui.adapter.ProductImageAdapter
import com.ushatech.aestoreskotlin.uitls.FragmentUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductDetailFragment : BaseFragment(), ProductImageAdapter.onEvent {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding:FragmentProductDetailBinding

    override fun onImageClick(position: Int, drawable: Drawable) {
        binding.ivMainProductImage.setImageDrawable(drawable)
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

        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        initClicks()
        binding.recvProductImages.adapter = ProductImageAdapter(fragmentContext,this)
        binding.recvProductImages.layoutManager = LinearLayoutManager(fragmentContext,LinearLayoutManager.HORIZONTAL,false)

        binding.tvMaxPrice.setPaintFlags(binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)



        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initClicks() {
        binding.ivCart.setOnClickListener {
            FragmentUtils().replaceFragmentBackStack(requireFragmentManager(),R.id.activity_main_nav_host_fragment,CartFragment(),CartFragment::class.java.canonicalName,true)


        }
        binding.ivMainProductImage.setOnClickListener {
            var dialogBinding = ImageDialogBinding.inflate(LayoutInflater.from(fragmentContext))
            var dialog = Dialog(fragmentContext)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.imageDilag.setImageDrawable(binding.ivMainProductImage.drawable)
            dialog.show()

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