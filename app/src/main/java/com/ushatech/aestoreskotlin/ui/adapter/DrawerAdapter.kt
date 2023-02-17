package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.CategoryItemBinding

class DrawerAdapter(var context: Context) :RecyclerView.Adapter<DrawerAdapter.MyViewholder>(){

    lateinit var binding: CategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {




    }

    override fun getItemCount(): Int {
        return 7
    }

    class MyViewholder(var categoryItemBinding: CategoryItemBinding):RecyclerView.ViewHolder(categoryItemBinding.root)
}