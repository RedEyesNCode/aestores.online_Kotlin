package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.FeaturedCategoryItemBinding

class FeaturedCategoryAdapter(var context: Context, var data:ArrayList<HomeScreenResponse.Categories>,var onClickCategoryActivity: onClickCategory):RecyclerView.Adapter<FeaturedCategoryAdapter.MyViewholder>() {


    private lateinit var binding: FeaturedCategoryItemBinding

    private var onClickEvent = onClickCategoryActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = FeaturedCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewholder(binding)


    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.binding.tvCategoryName.text = data.get(position).name
        Glide.with(context).load(data.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivCategory)
        holder.binding.tvItemCount.text = "${data.get(position).productCounts.toString()} Items. "

        binding.mainLayout.setOnClickListener {
            onClickEvent.onCategoryClick(position,data.get(position).id.toString())

        }


    }

    override fun getItemCount(): Int {

        return data.size

    }
    public interface onClickCategory{
        fun onCategoryClick(position: Int, toString: String)

    }

    class MyViewholder(var binding:FeaturedCategoryItemBinding):RecyclerView.ViewHolder(binding.root)
}