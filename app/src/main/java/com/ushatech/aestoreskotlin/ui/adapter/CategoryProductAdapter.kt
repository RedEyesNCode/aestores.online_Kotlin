package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.CategoryProductListBinding

class CategoryProductAdapter(var context: Context,var onClickEventActivity: onClickEvent):RecyclerView.Adapter<CategoryProductAdapter.MyViewHolder>() {


    private lateinit var binding: CategoryProductListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CategoryProductListBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Log.i("DEV-ASHUTOSH", "onBindViewHolder: CategoryProductAdapter")

        holder.binding.mainLayout.setOnClickListener {
            onClickEventActivity.onProductClick(position)


        }






    }

    override fun getItemCount(): Int {

        return 5
    }

    public interface onClickEvent{

        fun onProductClick(position: Int)
    }

    class MyViewHolder(var binding:CategoryProductListBinding) :RecyclerView.ViewHolder(binding.root)
}