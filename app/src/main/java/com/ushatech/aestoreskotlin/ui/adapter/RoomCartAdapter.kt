package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.tables.UserCartTable
import com.ushatech.aestoreskotlin.databinding.CartItemBinding

class RoomCartAdapter(var context: Context,var cartLocalData:ArrayList<UserCartTable>,var onRoomCartEventAct: onRoomCartEvent) :RecyclerView.Adapter<RoomCartAdapter.RoomCartViewHolder>(){


    lateinit var cartItemBinding: CartItemBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomCartViewHolder {
        cartItemBinding = CartItemBinding.inflate(LayoutInflater.from(context),parent,false)




        return RoomCartViewHolder(cartItemBinding)



    }

    override fun onBindViewHolder(holder: RoomCartViewHolder, position: Int) {
        // Setup the data for the local adapter from Room Data.
        val cartItemData = cartLocalData.get(position)

        holder.binding.tvFeatureOneTitle.text = cartItemData.productName
        holder.binding.tvItemWeight.text = ""
        holder.binding.tvTotalPrice.text = cartItemData.priceTotal
        holder.binding.tvRealPrice.text = cartItemData.priceProduct
        holder.binding.tvCartItemQuantity.text = cartItemData.quantity


        Glide.with(context).load(cartItemData.imageUrl).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivFeatureOne)


        holder.binding.ivDelete.setOnClickListener {


            onRoomCartEventAct.onDeleteItem(cartItemData,position)

        }
        holder.binding.tvIncre.setOnClickListener {



            val currentQuantity = holder.binding.tvCartItemQuantity.text.toString().toInt()
            if(currentQuantity>=1){
                holder.binding.tvCartItemQuantity.text = (currentQuantity+1).toString()
                cartItemData.quantity = holder.binding.tvCartItemQuantity.text.toString()
                cartItemData.priceTotal = (cartItemData.priceProduct.toInt() * cartItemData.quantity.toInt()).toString()
                onRoomCartEventAct.onUpdateItem(cartItemData,position)
            }

        }
        holder.binding.tvDecre.setOnClickListener {
            val currentQuantity = holder.binding.tvCartItemQuantity.text.toString().toInt()
            if(currentQuantity>1){
                holder.binding.tvCartItemQuantity.text = (currentQuantity-1).toString()
                cartItemData.quantity = holder.binding.tvCartItemQuantity.text.toString()
                cartItemData.priceTotal = (cartItemData.priceProduct.toInt() * cartItemData.quantity.toInt()).toString()

                onRoomCartEventAct.onUpdateItem(cartItemData,position)

            }

        }













    }

    override fun getItemCount(): Int {

        return cartLocalData.size



    }

    interface onRoomCartEvent{
        fun onDeleteItem(cartTable: UserCartTable, position: Int)
        fun onUpdateItem(cartTable: UserCartTable,position: Int)


    }

    class RoomCartViewHolder(var binding:CartItemBinding):RecyclerView.ViewHolder(binding.root)
}