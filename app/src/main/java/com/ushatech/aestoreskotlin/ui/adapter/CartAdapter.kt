package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.CartUserResponse
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.CartItemBinding

class CartAdapter(var context: Context, var dataCart: ArrayList<CartUserResponse.Items>,var onApiCartEventAct:CartAdapter.onApiCartEvent):RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    lateinit var binding: CartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CartItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val cartItemData = dataCart.get(position)

        holder.binding.tvFeatureOneTitle.text = cartItemData.name
        holder.binding.tvItemWeight.text = cartItemData.weight
        holder.binding.tvTotalPrice.text = cartItemData.totalPrice.toString()
        holder.binding.tvRealPrice.text = cartItemData.price
        holder.binding.tvCartItemQuantity.text = cartItemData.quantity


        Glide.with(context).load(cartItemData.image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivFeatureOne)


        holder.binding.ivDelete.setOnClickListener {
            onApiCartEventAct.onDeleteItemApi(cartItemData,position)

        }
        holder.binding.tvIncre.setOnClickListener {



            val currentQuantity = holder.binding.tvCartItemQuantity.text.toString().toInt()
            if(currentQuantity>=1){
                holder.binding.tvCartItemQuantity.text = (currentQuantity+1).toString()
                cartItemData.quantity = holder.binding.tvCartItemQuantity.text.toString()
                onApiCartEventAct.onUpdateItemApi(cartItemData,holder.binding.tvCartItemQuantity.text.toString().toInt())
            }

        }
        holder.binding.tvDecre.setOnClickListener {
            val currentQuantity = holder.binding.tvCartItemQuantity.text.toString().toInt()
            if(currentQuantity>1){
                holder.binding.tvCartItemQuantity.text = (currentQuantity-1).toString()
                cartItemData.quantity = holder.binding.tvCartItemQuantity.text.toString()
                onApiCartEventAct.onUpdateItemApi(cartItemData,holder.binding.tvCartItemQuantity.text.toString().toInt())

            }

        }

    }

    override fun getItemCount(): Int {
        return dataCart.size
    }

    public interface onApiCartEvent{
        fun onDeleteItemApi(cartTable: CartUserResponse.Items, quantity: Int)
        fun onUpdateItemApi(cartTable: CartUserResponse.Items, quantity: Int)



    }

    public class MyViewHolder(var binding:CartItemBinding):RecyclerView.ViewHolder(binding.root)
}