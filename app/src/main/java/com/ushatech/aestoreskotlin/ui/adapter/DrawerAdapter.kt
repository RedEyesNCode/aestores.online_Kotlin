package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.CategoryItemBinding

class DrawerAdapter(var context: Context) :RecyclerView.Adapter<DrawerAdapter.MyViewholder>(){

    lateinit var binding: CategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)



        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {

//        holder.categoryItemBinding.recvSubCategory.adapter = DrawerAdapter(context)
//        holder.categoryItemBinding.recvSubCategory.layoutManager = LinearLayoutManager(context)
//        holder.categoryItemBinding.tvCategoryName.setOnClickListener {
//            if(binding.recvSubCategory.visibility== View.VISIBLE){
//                binding.recvSubCategory.visibility = View.GONE
//            }else{
//                binding.recvSubCategory.visibility = View.VISIBLE
//            }
//
//
//        }



    }

    override fun getItemCount(): Int {
        return 7
    }

    class MyViewholder(var categoryItemBinding: CategoryItemBinding):RecyclerView.ViewHolder(categoryItemBinding.root)
}