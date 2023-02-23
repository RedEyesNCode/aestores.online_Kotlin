package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.databinding.SubcategoryItemBinding

class SubcategoryAdapter(
    var context: Context,
    var subcategories: AllCategoryResponse.Subcategories,
    var size: Int
):RecyclerView.Adapter<SubcategoryAdapter.MyViewholder> (){


    lateinit var binding: SubcategoryItemBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = SubcategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)



        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Log.i("DEV_ASHUTOSH", "onBindViewHolder: ${subcategories.name}")
        holder.subcategoryItemBinding.tvSubcategoryName.text = subcategories.name



    }

    override fun getItemCount(): Int {
        return size
    }

    class MyViewholder(var subcategoryItemBinding: SubcategoryItemBinding): RecyclerView.ViewHolder(subcategoryItemBinding.root)


}