package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.ImageItemBinding

class ImageViewPagerAdapter(var context:Context) :RecyclerView.Adapter<ImageViewPagerAdapter.MyViewholder>(){

    lateinit var binding: ImageItemBinding




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = ImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewholder(binding)


    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 2
    }

    class MyViewholder(var binding:ImageItemBinding):RecyclerView.ViewHolder(binding.root)
}