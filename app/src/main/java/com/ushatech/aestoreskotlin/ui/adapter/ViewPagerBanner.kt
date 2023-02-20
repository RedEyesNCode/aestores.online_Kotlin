package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.BannerItemBinding
import com.ushatech.aestoreskotlin.databinding.ImageItemBinding

class ViewPagerBanner(var context: Context, var array:ArrayList<HomeScreenResponse.Slides>) :RecyclerView.Adapter<ViewPagerBanner.MyViewholder>() {

    lateinit var binding: BannerItemBinding







    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerBanner.MyViewholder {

        binding = BannerItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewPagerBanner.MyViewholder(binding)

    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {

        Glide.with(context).load(array.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivBanner)

    }

    override fun getItemCount(): Int {

        return array.size
    }

   public class MyViewholder(var binding: BannerItemBinding): RecyclerView.ViewHolder(binding.root)

}