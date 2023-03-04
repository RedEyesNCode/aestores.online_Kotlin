package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.ImageItemBinding

class ImageViewPagerTrendingAdapter(var context:Context, var array:ArrayList<HomeScreenResponse.Trending>,var onEventActivity:onEventTrendingViewPager) :RecyclerView.Adapter<ImageViewPagerTrendingAdapter.MyViewholder>(){

    lateinit var binding: ImageItemBinding




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = ImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewholder(binding)


    }
    interface onEventTrendingViewPager{
        fun onProductClick(position:Int,productId:String)

    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.binding.tvMaxPrice.setPaintFlags(holder.binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.binding.tvMaxPriceTwo.setPaintFlags(holder.binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
//        holder.binding.tvMaxPrice.text = "Rs "+array.get(position).mrp.toString()
//        holder.binding.tvRealPrice.text = "Rs "+array.get(position).price.toString()
//        holder.binding.tvDiscountPercentageOne.text = ""
//
//        holder.binding.tvProductNameOne.text = array.get(position).name
//        Glide.with(context).load(array.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)


        if(array.size%2==0){

            //even size.
            if(position==0){
                // For position 0,1.

                holder.binding.tvProductNameOne.text = array.get(position).name
                Glide.with(context).load(array.get(position).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)
                holder.binding.tvMaxPrice.text ="Rs ${array.get(position).mrp}"
                holder.binding.tvRealPrice.text = "Rs ${array.get(position).price}"

                holder.binding.ivProductImage1.setOnClickListener {
                    onEventActivity.onProductClick(position,array.get(position).id.toString())

                }

                holder.binding.tvProductNameTwo.text = array.get(position+1).name
                Glide.with(context).load(array.get(position+1).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage2)
                holder.binding.tvMaxPriceTwo.text ="Rs ${array.get(position+1).mrp}"
                holder.binding.tvRealPriceTwo.text = "Rs ${array.get(position+1).price}"
                holder.binding.ivProductImage2.setOnClickListener {
                    onEventActivity.onProductClick(position,array.get(position+1).id.toString())

                }
            }else {

                try {
                    holder.binding.tvProductNameOne.text = array.get(position+1).name
                    Glide.with(context).load(array.get(position+1).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)
                    holder.binding.tvMaxPrice.text ="Rs ${array.get(position+1).mrp}"
                    holder.binding.tvRealPrice.text = "Rs ${array.get(position+1).price}"
                    holder.binding.ivProductImage1.setOnClickListener {
                        onEventActivity.onProductClick(position,array.get(position).id.toString())

                    }
                    holder.binding.tvProductNameTwo.text = array.get(position+2).name
                    Glide.with(context).load(array.get(position+2).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage2)
                    holder.binding.tvMaxPriceTwo.text ="Rs ${array.get(position+2).mrp}"
                    holder.binding.tvRealPriceTwo.text = "Rs ${array.get(position+2).price}"
                    holder.binding.ivProductImage2.setOnClickListener {
                        onEventActivity.onProductClick(position,array.get(position+1).id.toString())

                    }
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }

            }
        }


    }
    fun calcuateDiscount(markedprice: Double, s: Double): Double {
        return s * markedprice / 100
    }

    override fun getItemCount(): Int {
        return array.size/2
    }

    class MyViewholder(var binding:ImageItemBinding):RecyclerView.ViewHolder(binding.root)
}