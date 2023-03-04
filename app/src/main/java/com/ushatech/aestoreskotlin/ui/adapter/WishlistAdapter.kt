package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.WishlistItemBinding

class WishlistAdapter(var context:Context,var onEventWishlist:onEventWishlistAdapter):RecyclerView.Adapter<WishlistAdapter.WistlistViewHolder>() {


    lateinit var binding: WishlistItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WistlistViewHolder {

        binding = WishlistItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return WistlistViewHolder(binding)


    }

    override fun onBindViewHolder(holder: WistlistViewHolder, position: Int) {


        Toast.makeText(context,"STATIC DATA SHOWN !",Toast.LENGTH_LONG).show()
        Toast.makeText(context,"STATIC DATA SHOWN !",Toast.LENGTH_LONG).show()
        binding.ivDelete.setOnClickListener {
            onEventWishlist.onDeleteClick()
        }
        binding.ivProductImage1.setOnClickListener {
            onEventWishlist.onProductClick()

        }
    }

    override fun getItemCount(): Int {

        return 8

    }

    interface onEventWishlistAdapter{

        fun onDeleteClick()
        fun onProductClick()

    }
    class WistlistViewHolder(var binding:WishlistItemBinding):RecyclerView.ViewHolder(binding.root)
}