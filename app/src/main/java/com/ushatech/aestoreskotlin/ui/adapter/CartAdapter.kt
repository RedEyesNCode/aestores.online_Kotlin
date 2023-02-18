package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.CartItemBinding

class CartAdapter (var context: Context):RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    lateinit var binding: CartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CartItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("DEV_ASHUTOSH", "onBindViewHolder: CartAdapter ")
    }

    override fun getItemCount(): Int {
        return 2
    }

    public class MyViewHolder(var binding:CartItemBinding):RecyclerView.ViewHolder(binding.root)
}