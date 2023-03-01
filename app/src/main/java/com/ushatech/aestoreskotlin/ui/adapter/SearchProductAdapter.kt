package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.SearchProductResponse
import com.ushatech.aestoreskotlin.databinding.CategoryProductListBinding
import com.ushatech.aestoreskotlin.databinding.ProductGridListBinding

class SearchProductAdapter(var context:Context,var resultList:ArrayList<SearchProductResponse.Products>,var onEventClick:onClickSearch) :RecyclerView.Adapter<SearchProductAdapter.MyViewholder>(){

    private lateinit var binding:ProductGridListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {



        binding = ProductGridListBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val productDetail = resultList.get(position)
        holder.binding.tvMaxPrice.setPaintFlags(holder.binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.binding.tvRealPrice.text = productDetail.price
        holder.binding.tvMaxPrice.text = productDetail.mrp
        holder.binding.tvProductName.text = productDetail.name
        Glide.with(context).load(productDetail.image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage)

        holder.binding.mainLayout.setOnClickListener {
            onEventClick.onProductClick(position,productDetail.id.toString())

        }



    }
    interface onClickSearch{
        fun onProductClick(position: Int,productId:String)

    }

    override fun getItemCount(): Int {

        return resultList.size

    }

    public class MyViewholder(var binding:ProductGridListBinding) :RecyclerView.ViewHolder(binding.root)
}