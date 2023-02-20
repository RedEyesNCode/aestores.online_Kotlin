package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.ImageItemBinding

class ImageViewPagerArrivalAdapter(var context: Context, var array:ArrayList<HomeScreenResponse.Arrival>) :RecyclerView.Adapter<ImageViewPagerTrendingAdapter.MyViewholder>() {
    lateinit var binding: ImageItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewPagerTrendingAdapter.MyViewholder {
        binding = ImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return ImageViewPagerTrendingAdapter.MyViewholder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageViewPagerTrendingAdapter.MyViewholder,
        position: Int
    ) {

        var arrival = array.get(position)

        holder.binding.tvProductNameOne.text = arrival.name
        Glide.with(context).load(arrival.image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)
        holder.binding.tvMaxPrice.text ="Rs ${arrival.mrp}"
        holder.binding.tvRealPrice.text = "Rs ${arrival.price}"




    }

    override fun getItemCount(): Int {
        return array.size
    }

    class MyViewholder(var binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root)
}