package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.SearchProductResponse
import com.ushatech.aestoreskotlin.databinding.CategoryProductListBinding

class CategoryProductAdapter(var context: Context,var data:SearchProductResponse.Data,var onClickEventActivity: onClickEvent):RecyclerView.Adapter<CategoryProductAdapter.MyViewHolder>() {


    private lateinit var binding: CategoryProductListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CategoryProductListBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Log.i("DEV-ASHUTOSH", "onBindViewHolder: CategoryProductAdapter")
        val currentProduct = data.products.get(position)

        holder.binding.tvCategoryNameOne.setText(currentProduct.name.toString())
        holder.binding.tvMaxPrice.setText("Rs ${currentProduct.mrp.toString()}")
        holder.binding.tvRealPrice.setText("Rs ${currentProduct.price.toString()}")
        holder.binding.tvDiscountPercentage.visibility = View.GONE


        Glide.with(context).load(currentProduct.image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage)



        holder.binding.mainLayout.setOnClickListener {
            onClickEventActivity.onProductClick(position,currentProduct.id.toString())


        }






    }

    override fun getItemCount(): Int {

        return 5
    }

    public interface onClickEvent{

        fun onProductClick(position: Int, toString: String)
    }

    class MyViewHolder(var binding:CategoryProductListBinding) :RecyclerView.ViewHolder(binding.root)
}