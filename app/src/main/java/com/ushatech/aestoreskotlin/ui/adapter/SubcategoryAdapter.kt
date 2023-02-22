package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.databinding.CategoryItemBinding
import com.ushatech.aestoreskotlin.databinding.SubcategoryItemBinding

class SubcategoryAdapter(var context:Context,var subcategories: ArrayList<AllCategoryResponse.Subcategories>):RecyclerView.Adapter<SubcategoryAdapter.MyViewholder> (){


    lateinit var binding: SubcategoryItemBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = SubcategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)



        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Log.i("DEV_ASHUTOSH", "onBindViewHolder: ${subcategories.get(position).name}")
        holder.subcategoryItemBinding.tvSubcategoryName.text = subcategories.get(position).name



    }

    override fun getItemCount(): Int {
        return subcategories.size
    }

    class MyViewholder(var subcategoryItemBinding: SubcategoryItemBinding): RecyclerView.ViewHolder(subcategoryItemBinding.root)


}