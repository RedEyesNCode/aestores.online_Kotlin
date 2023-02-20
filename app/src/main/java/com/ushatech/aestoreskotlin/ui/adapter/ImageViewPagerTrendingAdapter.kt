package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.ImageItemBinding

class ImageViewPagerTrendingAdapter(var context:Context, var array:ArrayList<HomeScreenResponse.Trending>) :RecyclerView.Adapter<ImageViewPagerTrendingAdapter.MyViewholder>(){

    lateinit var binding: ImageItemBinding




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = ImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewholder(binding)


    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.binding.tvMaxPrice.setPaintFlags(holder.binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.binding.tvMaxPrice.text = "Rs "+array.get(position).mrp.toString()
        holder.binding.tvRealPrice.text = "Rs "+array.get(position).price.toString()
        holder.binding.tvDiscountPercentageOne.text = ""

        holder.binding.tvProductNameOne.text = array.get(position).name
        Glide.with(context).load(array.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)

//        if(array.get(position).mrp.toString().equals(array.get(position).price.toString())){
//
//            holder.binding.tvDiscountPercentageOne.text = ""
//
//        }else {
//            try {
//                holder.binding.tvDiscountPercentageOne.text = calcuateDiscount(array.get(position).mrp!!.toDouble(),array.get(position).price!!.toDouble()).toString()
//            }catch (e:java.lang.Exception){
//                e.printStackTrace()
//
//                holder.binding.tvDiscountPercentageOne.text = ""
//
//            }
//
//
//        }



    }
    fun calcuateDiscount(markedprice: Double, s: Double): Double {
        return s * markedprice / 100
    }

    override fun getItemCount(): Int {
        return 2
    }

    class MyViewholder(var binding:ImageItemBinding):RecyclerView.ViewHolder(binding.root)
}