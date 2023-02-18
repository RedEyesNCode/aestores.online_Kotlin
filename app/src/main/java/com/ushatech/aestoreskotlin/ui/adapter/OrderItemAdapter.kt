package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.databinding.OrderItemBinding

class OrderItemAdapter (var context: Context):RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>(){

    private lateinit var binding: OrderItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = OrderItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("DEV_ASHUTOSH", "onBindViewHolder: OrderItemAdapter")
    }

    override fun getItemCount(): Int {
        return  2
    }

    public class MyViewHolder(var binding:OrderItemBinding):RecyclerView.ViewHolder(binding.root)
}