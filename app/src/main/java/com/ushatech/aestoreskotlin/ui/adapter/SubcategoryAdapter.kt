package com.ushatech.aestoreskotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ushatech.aestoreskotlin.data.SubCateogryResponse
import com.ushatech.aestoreskotlin.databinding.SubcategoryItemBinding
import com.ushatech.aestoreskotlin.presentation.DashboardViewModel

class SubcategoryAdapter(
    var context: Context,
    var subcategories: ArrayList<SubCateogryResponse.Data>,
    var size: Int
):RecyclerView.Adapter<SubcategoryAdapter.MyViewholder> (){


    lateinit var binding: SubcategoryItemBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = SubcategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)



        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Log.i("DEV_ASHUTOSH", "onBindViewHolder: ${subcategories.get(position).name}")
        holder.subcategoryItemBinding.tvSubcategoryName.text = subcategories.get(position).name

        holder.subcategoryItemBinding.tvSubcategoryName.setOnClickListener {
            val dashboardViewModel = DashboardViewModel()
            dashboardViewModel.getAllSuperCategory(subcategories.get(position).id.toString())
            dashboardViewModel.subCategoryResponse.observe((context as LifecycleOwner),{
                if(it!=null){
                    Log.i("DEV_ASHUTOSH", "showLog:${subcategories.get(position).name} -->  ${it.data.size}")

                }
            })




        }


    }

    override fun getItemCount(): Int {
        return size
    }

    class MyViewholder(var subcategoryItemBinding: SubcategoryItemBinding): RecyclerView.ViewHolder(subcategoryItemBinding.root)


}