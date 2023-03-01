package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.ProductDetailResponseData
import com.ushatech.aestoreskotlin.databinding.ProductImageItemBinding

class ProductImageAdapter(var context:Context,var productImages:ArrayList<ProductDetailResponseData.MoreImages>,var event:onEvent):RecyclerView.Adapter<ProductImageAdapter.Myviewholder>() {

    lateinit var binding: ProductImageItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        binding = ProductImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return Myviewholder(binding)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        Glide.with(context).load(productImages.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage)

        holder.binding.ivProductImage.setOnClickListener {
            event.onImageClick(position,productImages.get(position).image.toString())

        }

    }

    public interface onEvent{
        fun onImageClick(position:Int, drawable: String)

    }

    override fun getItemCount(): Int {
        return productImages.size
    }

    class Myviewholder(var binding:ProductImageItemBinding) :RecyclerView.ViewHolder(binding.root)
}