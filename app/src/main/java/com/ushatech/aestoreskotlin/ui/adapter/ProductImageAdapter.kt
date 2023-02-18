package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.databinding.ProductImageItemBinding

class ProductImageAdapter(var context:Context,var event:onEvent):RecyclerView.Adapter<ProductImageAdapter.Myviewholder>() {

    lateinit var binding: ProductImageItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        binding = ProductImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return Myviewholder(binding)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        if(position==0){
            holder.binding.ivProductImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_banner_home))
        }else{
            holder.binding.ivProductImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_leaf))
        }

        holder.binding.ivProductImage.setOnClickListener {
            if(position==0){
                ContextCompat.getDrawable(context, R.drawable.ic_banner_home)
                    ?.let { it1 -> event.onImageClick(position, it1) }
            }else{
                ContextCompat.getDrawable(context, R.drawable.ic_leaf)
                    ?.let { it1 -> event.onImageClick(position, it1) }

            }

        }

    }

    public interface onEvent{
        fun onImageClick(position:Int,drawable:Drawable)

    }

    override fun getItemCount(): Int {
        return 2
    }

    class Myviewholder(var binding:ProductImageItemBinding) :RecyclerView.ViewHolder(binding.root)
}