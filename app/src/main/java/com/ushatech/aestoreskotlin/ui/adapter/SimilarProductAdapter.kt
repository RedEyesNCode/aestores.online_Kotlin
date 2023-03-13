package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.ProductDetailResponseData
import com.ushatech.aestoreskotlin.databinding.VariationItemBinding

class SimilarProductAdapter(var context: Context,var data:ArrayList<ProductDetailResponseData.SimilarProducts>,var onEventActivity:onEventSimilarProduct):RecyclerView.Adapter<SimilarProductAdapter.SimilarProductViewHolder>() {


    lateinit var binding: VariationItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarProductViewHolder {
        binding = VariationItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return SimilarProductViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SimilarProductViewHolder, position: Int) {
        holder.binding.tvProductName.text = data.get(position).name
        holder.binding.tvProductPrice.text = "Rs ${data.get(position).price}"
        Glide.with(context).load(data.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage)



        holder.binding.mainLayout.setOnClickListener {

            onEventActivity.onProductClick(position,data.get(position).id.toString())


        }

    }

    override fun getItemCount(): Int {

        return data.size

    }

    public interface onEventSimilarProduct{

        fun onProductClick(position: Int,productId:String)

    }

    class SimilarProductViewHolder(var binding:VariationItemBinding):RecyclerView.ViewHolder(binding.root)
}