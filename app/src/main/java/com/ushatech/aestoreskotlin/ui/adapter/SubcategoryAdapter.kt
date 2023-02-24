package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.MasterCategoryModel
import com.ushatech.aestoreskotlin.data.SubCateogryResponse
import com.ushatech.aestoreskotlin.databinding.SubcategoryItemBinding
import com.ushatech.aestoreskotlin.presentation.DashboardViewModel

class SubcategoryAdapter(
    var context: Context,
    var subcategories: ArrayList<SubCateogryResponse.Data>,
    var size: Int,
    var masterCategoryModel: MasterCategoryModel
):RecyclerView.Adapter<RecyclerView.ViewHolder> (){


    lateinit var binding: SubcategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = SubcategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)

        val viewHolder:RecyclerView.ViewHolder = when(viewType){
            MasterCategoryModel.SUB_CATEGORY->{
                MyViewholder(binding)
            }
            else -> MyViewholder(binding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewholder).subcategoryItemBinding.tvSubcategoryName.text = subcategories.get(position).name

        holder.subcategoryItemBinding.ivDown.setOnClickListener {

            val dashboardViewModel = DashboardViewModel()
            dashboardViewModel.getAllSuperCategory(subcategories.get(position).id.toString())
            dashboardViewModel.superCategoryResponse.observe((context as LifecycleOwner),{
                if(it!=null){
                    holder.subcategoryItemBinding.ivUp.visibility = View.VISIBLE
                    holder.subcategoryItemBinding.ivDown.visibility = View.GONE
                    holder.subcategoryItemBinding.recvSuperCategory.visibility =View.VISIBLE
                    (holder as MyViewholder).subcategoryItemBinding.recvSuperCategory.adapter = SuperCategoryAdapter(context,it.data)
                    (holder as MyViewholder).subcategoryItemBinding.recvSuperCategory.layoutManager = LinearLayoutManager(context)
                }
            })

        }

        holder.subcategoryItemBinding.ivUp.setOnClickListener {
            holder.subcategoryItemBinding.ivUp.visibility = View.GONE
            holder.subcategoryItemBinding.ivDown.visibility = View.VISIBLE
            holder.subcategoryItemBinding.recvSuperCategory.visibility =View.GONE


        }
    }

    override fun getItemCount(): Int {
        return subcategories.size
    }

    override fun getItemViewType(position: Int): Int {
        return masterCategoryModel.type
    }

    class MyViewholder(var subcategoryItemBinding: SubcategoryItemBinding): RecyclerView.ViewHolder(subcategoryItemBinding.root)


}