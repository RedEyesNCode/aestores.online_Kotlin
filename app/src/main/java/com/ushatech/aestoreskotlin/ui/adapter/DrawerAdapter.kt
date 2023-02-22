package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.databinding.CategoryItemBinding
import com.ushatech.aestoreskotlin.databinding.SubcategoryItemBinding


class DrawerAdapter(
    var context: Context,
    var allCategoryResponse: AllCategoryResponse,
    var subCategories: ArrayList<AllCategoryResponse.Subcategories>
) :RecyclerView.Adapter<DrawerAdapter.MyViewholder>(){

    lateinit var binding: CategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)



        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {

        val categories = allCategoryResponse.data.get(position)

        holder.categoryItemBinding.tvCategoryName.text = categories.name







        holder.categoryItemBinding.tvCategoryName.setOnClickListener {

            if(binding.recvSubCategory.visibility== View.VISIBLE){
                binding.recvSubCategory.visibility = View.GONE
                binding.ivDown.visibility = View.VISIBLE
                binding.ivUp.visibility = View.GONE
            }else{
                binding.recvSubCategory.visibility = View.VISIBLE
                binding.ivDown.visibility = View.GONE
                binding.ivUp.visibility = View.VISIBLE
            }
        }



    }

    override fun getItemCount(): Int {
        return allCategoryResponse.data.size
    }

    class MyViewholder(var categoryItemBinding: CategoryItemBinding):RecyclerView.ViewHolder(categoryItemBinding.root)
    class MyViewholderSubCategory(var subcategoryItemBinding: SubcategoryItemBinding): RecyclerView.ViewHolder(subcategoryItemBinding.root)

}