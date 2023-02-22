package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.databinding.ImageItemBinding

class ImageViewPagerArrivalAdapter(var context: Context, var array:ArrayList<HomeScreenResponse.Arrival>) :RecyclerView.Adapter<ImageViewPagerTrendingAdapter.MyViewholder>() {
    lateinit var binding: ImageItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewPagerTrendingAdapter.MyViewholder {
        binding = ImageItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return ImageViewPagerTrendingAdapter.MyViewholder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageViewPagerTrendingAdapter.MyViewholder,
        position: Int
    ) {

        val arrival = array.get(position)
        holder.binding.tvMaxPrice.setPaintFlags(holder.binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.binding.tvMaxPriceTwo.setPaintFlags(holder.binding.tvMaxPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        if(array.size%2==0){

            //even size.
            if(position==0){
                // For position 0,1.

                holder.binding.tvProductNameOne.text = arrival.name
                Glide.with(context).load(arrival.image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)
                holder.binding.tvMaxPrice.text ="Rs ${arrival.mrp}"
                holder.binding.tvRealPrice.text = "Rs ${arrival.price}"

                holder.binding.tvProductNameTwo.text = array.get(position+1).name
                Glide.with(context).load(array.get(position+1).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage2)
                holder.binding.tvMaxPriceTwo.text ="Rs ${array.get(position+1).mrp}"
                holder.binding.tvRealPriceTwo.text = "Rs ${array.get(position+1).price}"
            }else {

                try {
                    holder.binding.tvProductNameOne.text = array.get(position+1).name
                    Glide.with(context).load(array.get(position+1).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage1)
                    holder.binding.tvMaxPrice.text ="Rs ${array.get(position+1).mrp}"
                    holder.binding.tvRealPrice.text = "Rs ${array.get(position+1).price}"

                    holder.binding.tvProductNameTwo.text = array.get(position+2).name
                    Glide.with(context).load(array.get(position+2).image).placeholder(R.drawable.ic_banner_home).into(holder.binding.ivProductImage2)
                    holder.binding.tvMaxPriceTwo.text ="Rs ${array.get(position+2).mrp}"
                    holder.binding.tvRealPriceTwo.text = "Rs ${array.get(position+2).price}"
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }

            }
        }







        // Checking if the size coming from the Api is odd or even




    }

    override fun getItemCount(): Int {
        return array.size/2
    }

    class MyViewholder(var binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root)
}