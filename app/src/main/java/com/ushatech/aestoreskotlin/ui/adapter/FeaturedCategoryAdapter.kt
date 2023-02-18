package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.R
import com.ushatech.aestoreskotlin.databinding.FeaturedCategoryItemBinding

class FeaturedCategoryAdapter(var context: Context, var onClickCategoryActivity: onClickCategory):RecyclerView.Adapter<FeaturedCategoryAdapter.MyViewholder>() {


    private lateinit var binding: FeaturedCategoryItemBinding

    private var onClickEvent = onClickCategoryActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = FeaturedCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewholder(binding)


    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        when(position){
            0->{
                holder.binding.tvCategoryName.setText(context.getString(R.string.skin_care_beauty))
            }
            1->{
                holder.binding.tvCategoryName.setText(context.getString(R.string.hair_care))

            }
            2->{

                holder.binding.tvCategoryName.setText(context.getString(R.string.health_care))

            }
            3->{
                holder.binding.tvCategoryName.setText(context.getString(R.string.personal_care))

            }
        }
        binding.mainLayout.setOnClickListener {
            onClickEvent.onCategoryClick(position)

        }


    }

    override fun getItemCount(): Int {

        return 5

    }
    public interface onClickCategory{
        fun onCategoryClick(position:Int)

    }

    class MyViewholder(var binding:FeaturedCategoryItemBinding):RecyclerView.ViewHolder(binding.root)
}